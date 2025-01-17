package com.project.booking.controllers;

import com.project.booking.dtos.HotelDTO;
import com.project.booking.models.HotelFacility;
import com.project.booking.models.Hotel;
import com.project.booking.models.HotelImage;
import com.project.booking.response.FeedbackDetailResponse;
import com.project.booking.response.FeedbackSummaryResponse;
import com.project.booking.response.RoomDetailResponse;
import com.project.booking.services.FeedbackDetailService;
import com.project.booking.services.FeedbackSummaryService;
import com.project.booking.services.HotelImageService;
import com.project.booking.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelImageService hotelImageService;
    private final FeedbackDetailService feedbackDetailService;
    private final FeedbackSummaryService feedbackSummaryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPlace(
            @RequestBody HotelDTO hotelDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Hotel newHotel = hotelService.addPlace(hotelDTO);
            return ResponseEntity.ok(newHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // trả về danh sách tất cả phòng
    @GetMapping("/hotels")
    public String renderHotelList(
            @RequestParam(value = "Page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "Limit", required = false, defaultValue = "16") int limit,
            Model model) {

        // Kiểm soát giá trị limit tối đa
        limit = Math.min(limit, 50); // Giới hạn tối đa là 50

        // Lấy danh sách khách sạn từ service
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("rating").ascending());
        Page<Hotel> hotelPage = hotelService.getAllPlace(pageRequest);

        // Thêm dữ liệu vào model để Thymeleaf có thể sử dụng
        model.addAttribute("hotels", hotelPage.getContent());
        model.addAttribute("totalPages", hotelPage.getTotalPages());
        model.addAttribute("currentPage", page);

        // Trả về tên của view (file Listphong.html)
        return "Listphong";
    }

    @PostMapping(value = "uploads/thumbs/{hotel_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPicture(
            @Valid @ModelAttribute HotelDTO hotelDTO,
            @PathVariable("hotel_id") Long hotelId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be null or empty");
            }
            Hotel existingHotel = hotelService.getHotelById(hotelId);

            //Kiem tra kich thuoc file va dinh dang
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Maximum size is 10MB only");
            }

            String contentFile = file.getContentType();
            if (contentFile == null || !contentFile.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an Image");
            }

            String filename = storeFile(file);
            String fileUrl = "http://localhost:8088/hotel/image/" + filename;
            // Update the existing picture with the new image URL and details
            hotelDTO.setImage(filename);
            Hotel updatedHoted = hotelService.updateThumbnail(
                    hotelId,
                    HotelDTO.builder()
                            .image(fileUrl)
                            .build()
            );
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //THEM UUID TRUOC FILE DE DAM BAO FILE LA DUY NHAT
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        //DUONG DAN DEN THU MUC LUU FILE
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //KIEM TRA THU MUC TON TAI CHUA
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        //DUONG DAN DEN FILE
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }


    @GetMapping("/image/{name}")
    public ResponseEntity<?> viewPicture(@PathVariable String name) {
        try {
            java.nio.file.Path picturePath = Paths.get("D:/Documents/DEV/Project1/Backup/Backend/uploads/" + name);
            UrlResource resource = new UrlResource(picturePath.toUri());

            String contentType = URLConnection.guessContentTypeFromName(picturePath.toString());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))  // Automatically handles Content-Type header
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{hotelId}")
    public String getHotel(
            @PathVariable("hotelId") Long hotelId,
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            Model model
    ) {
        try {
            Hotel hotel = hotelService.getHotelById(hotelId);
            System.out.println(hotel.getId());
//            if (hotel == null) {
//                model.addAttribute("error", "Khách sạn không tồn tại.");
//                return "errorPage";
//            }

            List<RoomDetailResponse> roomDetailResponseList = new ArrayList<>();

            LocalDate today = LocalDate.now();

            boolean available = false;
            if (checkIn != null && checkOut != null) {
                LocalDate checkInDate = LocalDate.parse(checkIn);
                LocalDate checkOutDate = LocalDate.parse(checkOut);

                // Check if check-out is before check-in, check-in is before today, or check-out is after today
                if (checkOutDate.isBefore(checkInDate) || checkInDate.isBefore(today) || checkOutDate.isBefore(today)) {
                    checkIn = null;
                    checkOut = null;
                    model.addAttribute("error", "Ngày đặt không hợp lệ. Vui lòng kiểm tra lại.");
                } else {
                    roomDetailResponseList = hotelService.getRoomAvailable(hotelId, checkIn, checkOut);
                    available=true;
                }

            } else {
                roomDetailResponseList = hotelService.getRoomDetailResponse(hotelId);
            }


            FeedbackSummaryResponse feedbackSummary = feedbackSummaryService.getFeedbackOfHotel(hotelId);
            List<FeedbackDetailResponse> feedbackDetailResponses = feedbackDetailService
                    .getFeedbackDetailOfSummary(feedbackSummary.id)
                    .stream()
                    .map(FeedbackDetailResponse::fromDetail)
                    .collect(Collectors.toList());
            model.addAttribute("feedbackDetails", feedbackDetailResponses);
            model.addAttribute("roomDetailResponse", roomDetailResponseList);
            model.addAttribute("hotel", hotel);
            model.addAttribute("checkIn", checkIn != null ? checkIn : today.toString());
            model.addAttribute("checkOut", checkOut != null ? checkOut : today.plusDays(3).toString());
            model.addAttribute("availabilityChecked", available);
            return "House";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "House";
        }
    }
//    @GetMapping("/{hotelId}")
//    public String getHotel(
//            @PathVariable("hotelId") Long hotelId,
//            @RequestParam(required = false) String checkIn,
//            @RequestParam(required = false) String checkOut,
//            Model model
//    ){
//        try{
//            Hotel hotel = hotelService.getHotelById(hotelId);
//            List<RoomDetailResponse> roomDetailResponseList = new ArrayList<>();
//            boolean availabilityChecked = false;
//            LocalDate today = LocalDate.now();
//
//            if (checkIn != null && checkOut != null) {
//                LocalDate checkInDate = LocalDate.parse(checkIn);
//                LocalDate checkOutDate = LocalDate.parse(checkOut);
//
//                // Check if check-out is before check-in, check-in is before today, or check-out is after today
//                if (checkOutDate.isBefore(checkInDate) || checkInDate.isBefore(today) || checkOutDate.isBefore(today)) {
//                    checkIn = null;
//                    checkOut = null;
//                    model.addAttribute("error", "Ngày đặt không hợp lệ. Vui lòng kiểm tra lại.");
//                }else{
//                    roomDetailResponseList = hotelService.getRoomAvailable(hotelId, checkIn, checkOut);
//                    availabilityChecked = true;
//                }
//
//            } else {
//                roomDetailResponseList = hotelService.getRoomDetailResponse(hotelId);
//            }
//
//            model.addAttribute("roomDetailResponse", roomDetailResponseList);
//            model.addAttribute("hotel", hotel);
//            model.addAttribute("checkIn", checkIn != null ? checkIn : today.toString());
//            model.addAttribute("checkOut", checkOut != null ? checkOut : today.plusDays(3).toString());
//            model.addAttribute("availabilityChecked", availabilityChecked);
//            return "House";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "House";
//        }
//    }

    @DeleteMapping("delete/{hotelId}")
    public ResponseEntity<?> deleteHotel(
            @PathVariable("hotelId") Long hotelId
    ) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.ok("Delete successfully");
    }

    @PostMapping(value = "uploads/{hotel_id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPictures(
            @Valid @ModelAttribute HotelDTO hotelDTO,
            @PathVariable("hotel_id") Long hotelId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        try {
            if (files == null || files.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be null or empty");
            }
            Hotel existingHotel = hotelService.getHotelById(hotelId);
            List<String> fileUrls = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("One or more files are empty");
                }
                //Kiem tra kich thuoc file va dinh dang
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("Maximum size is 10MB only");
                }

                String contentFile = file.getContentType();
                if (contentFile == null || !contentFile.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an Image");
                }

                String filename = storeFile(file);
                String fileUrl = "http://localhost:8088/hotel/image/" + filename;
                // Update the existing picture with the new image URL and details
                HotelImage hotelImage = HotelImage.builder()
                        .hotel(existingHotel)
                        .imageUrl(fileUrl)
                        .build();
                hotelImageService.updateImage(hotelImage);

                fileUrls.add(fileUrl);
            }
            return ResponseEntity.ok(fileUrls);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/imageList/{hotelId}")
    public ResponseEntity<?> getHotelImage(
            @PathVariable("hotelId") Long hotelId
    ) {
        try {
            List<String> urlList = hotelService.getHotelImage(hotelId);
            return ResponseEntity.ok(urlList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public List<Hotel> getHotels() {
        // Lấy danh sách khách sạn từ HotelService
        return hotelService.getAllHotels();
    }


    public List<Hotel> searchHotels(String type, String region, String hotelName) {
        return hotelService.searchHotels(type, region, hotelName);
    }
}
