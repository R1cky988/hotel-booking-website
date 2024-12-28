package com.project.booking.controllers;

import com.project.booking.dtos.BookingDetailDTO;
import com.project.booking.models.BookingDetail;
import com.project.booking.response.BookingResponse;
import com.project.booking.services.BookingDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/booking")
@RestController
public class BookingDetailController {
    private final BookingDetailService bookingDetailService;

    @PostMapping("")
    public ResponseEntity<?> createForm(
            @Valid @RequestBody BookingDetailDTO bookingDetailDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            BookingDetail bookingDetail = bookingDetailService.createBookingForm(bookingDetailDTO);
           return ResponseEntity.ok("Booking successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{formId}")
    public ResponseEntity<?> getForm(@PathVariable("formId") Long formId){
        try{
            BookingResponse  bookingResponse = bookingDetailService.getBookingForm(formId);
            return ResponseEntity.ok(bookingResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{formId}")
    public ResponseEntity<?> deleteForm(@PathVariable("formId") Long formId){
        try{
            bookingDetailService.deleteBookingForm(formId);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
