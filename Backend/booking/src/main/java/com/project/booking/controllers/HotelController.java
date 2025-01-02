package com.project.booking.controllers;

import com.project.booking.dtos.HotelDTO;
import com.project.booking.models.HotelFacility;
import com.project.booking.models.Hotel;
import com.project.booking.models.HotelImage;
import com.project.booking.services.HotelImageService;
import com.project.booking.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
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

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelImageService hotelImageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPlace(
            @RequestBody HotelDTO hotelDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Hotel newHotel = hotelService.addPlace(hotelDTO);
            return ResponseEntity.ok(newHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<List<Hotel>> getAllPlace(
            @RequestParam("Page")int page, //number of Page
            @RequestParam("Limit") int limit //number of place in page
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("rating").ascending());
        Page<Hotel> hotelPage = hotelService.getAllPlace(pageRequest);
        int totalPages = hotelPage.getTotalPages();
        List<Hotel> hotels = hotelPage.getContent();
        return ResponseEntity.ok(hotels);
    }

    @PostMapping(value = "uploads/thumbs/{hotel_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPicture(
            @Valid @ModelAttribute HotelDTO hotelDTO,
            @PathVariable("hotel_id") Long hotelId,
            @RequestParam("file") MultipartFile file
    ){
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be null or empty");
            }
            Hotel existingHotel = hotelService.getHotelById(hotelId);

            //Kiem tra kich thuoc file va dinh dang
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Maximum size is 10MB only");
            }

            String contentFile = file.getContentType();
            if(contentFile == null || !contentFile.startsWith("image/")){
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
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //DUONG DAN DEN FILE
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<?> viewPicture(@PathVariable String name){
        try{
            java.nio.file.Path picturePath = Paths.get("uploads/" + name);
            UrlResource resource = new UrlResource(picturePath.toUri());

            String contentType = URLConnection.guessContentTypeFromName(picturePath.toString());

            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))  // Automatically handles Content-Type header
                        .body(resource);
            }
            else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }
    @GetMapping("/{hotelId}")
    public String getHotel(
            @PathVariable("hotelId") Long hotelId,
            Model model
    ){
        try{
            Hotel hotel = hotelService.getHotelById(hotelId);
            return "House";
            //return ResponseEntity.ok(hotel);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            //return ResponseEntity.badRequest().body(e.getMessage());
            return "House";
        }
    }

    @DeleteMapping("delete/{hotelId}")
    public ResponseEntity<?> deleteHotel(
            @PathVariable("hotelId") Long hotelId
    ){
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.ok("Delete successfully");
    }

    @PostMapping(value = "uploads/{hotel_id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPictures(
            @Valid @ModelAttribute HotelDTO hotelDTO,
            @PathVariable("hotel_id") Long hotelId,
            @RequestParam("files") List<MultipartFile> files
    ){
        try {
            if (files == null || files.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be null or empty");
            }
            Hotel existingHotel = hotelService.getHotelById(hotelId);
            List<String> fileUrls = new ArrayList<>();

            for(MultipartFile file : files){
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("One or more files are empty");
                }
                //Kiem tra kich thuoc file va dinh dang
                if(file.getSize() > 10 * 1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("Maximum size is 10MB only");
                }

                String contentFile = file.getContentType();
                if(contentFile == null || !contentFile.startsWith("image/")){
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
    ){
        try{
            List<String> urlList = hotelService.getHotelImage(hotelId);
            return ResponseEntity.ok(urlList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/facility/{hotelId}")
    public ResponseEntity<?> getHotelFacility(
            @PathVariable("hotelId") Long hotelId
    ){
        try{
            List<HotelFacility> urlList = hotelService.getHotelFacility(hotelId);
            return ResponseEntity.ok(urlList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
