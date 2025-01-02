package com.project.booking.controllers;

import com.project.booking.dtos.FeedbackDetailDTO;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.services.FeedbackDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackDetailController {
    public final FeedbackDetailService feedbackDetailService;

    @PostMapping("")
    public ResponseEntity<?> createFeedback(
            @Valid @RequestBody FeedbackDetailDTO feedbackDetailDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            FeedbackDetail feedbackDetail = feedbackDetailService.createFeedback(feedbackDetailDTO);
            return ResponseEntity.ok(feedbackDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("")

}
