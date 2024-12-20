package com.project.booking.controllers;

import com.project.booking.dtos.RoomDetailDTO;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.RoomDetail;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.services.RoomDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomDetailController {
    private final RoomDetailService roomDetailService;
    private final RoomDetailRepository roomDetailRepository;

//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadRoom(
//            @RequestBody RoomDetailDTO roomDetailDTO,
//            BindingResult result
//    ){
//        try{
//            if(result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            RoomDetail newRoom = roomDetailService.createRoom(roomDetailDTO);
//            return ResponseEntity.ok(newRoom);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<?> getAllRooms(
        @PathVariable("placeId") Long placeId
    ) {
        try{
            List<RoomDetail> roomDetailList = roomDetailService.getAllRoomOfPlace(placeId);
            return ResponseEntity.ok(roomDetailList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
