package com.project.booking.controllers;

import com.project.booking.dtos.HotelDTO;
import com.project.booking.dtos.RoomDetailDTO;
import com.project.booking.models.*;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.services.HotelService;
import com.project.booking.services.RoomDetailService;
import com.project.booking.services.RoomImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomDetailController {
    private final RoomDetailService roomDetailService;
    private final RoomDetailRepository roomDetailRepository;
    private final RoomImageService roomImageService;
    private final HotelService hotelService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadRoom(
            @RequestBody RoomDetailDTO roomDetailDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            RoomDetail newRoom = roomDetailService.createRoom(roomDetailDTO);
            return ResponseEntity.ok(newRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public String getAllRooms(
            @RequestParam("hotelId") Long hotelId,
            @RequestParam("roomId") Long roomId,
            Model model
    ) {
        try {
            RoomDetail roomDetail = roomDetailService.getRoomById(roomId);
            model.addAttribute("roomDetail", roomDetail);
            return "RoomDetail";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "RoomDetail";
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableRooms(
            @RequestParam Long hotelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkInDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate) {

        List<RoomDetail> availableRooms = roomDetailService.getAvailableRooms(hotelId, checkInDate, checkOutDate);

        if (!availableRooms.isEmpty()) {
            return ResponseEntity.ok(availableRooms);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No rooms available for the selected dates.");
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

    @PostMapping(value = "uploads/{roomId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPictures(
            @Valid @ModelAttribute HotelDTO hotelDTO,
            @PathVariable("roomId") Long roomId,
            @RequestParam("files") List<MultipartFile> files
    ){
        try {
            if (files == null || files.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be null or empty");
            }
            RoomDetail roomDetail = roomDetailService.getRoomById(roomId);
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
                String fileUrl = "http://localhost:8088/room/image/" + filename;
                // Update the existing picture with the new image URL and details
                RoomImage roomImage = RoomImage.builder()
                        .roomDetail(roomDetail)
                        .largeUrl(fileUrl)
                        .build();
                roomImageService.uploadRoomImage(roomImage);

                fileUrls.add(fileUrl);
            }
            return ResponseEntity.ok(fileUrls);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<?> viewPicture(@PathVariable String name) {
        try {
            java.nio.file.Path picturePath = Paths.get("D:/Documents/DEV/Project1/Backup/Backend/uploads/"+ name);
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

}
