package com.project.booking.controllers;

import com.project.booking.dtos.FeedbackSummaryDTO;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.response.FeedbackSummaryResponse;
import com.project.booking.services.FeedbackSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/summary")
@RequiredArgsConstructor
public class FeedbackSummaryController {
    private final FeedbackSummaryService feedbackSummaryService;

    @PostMapping("")
    public ResponseEntity<?> createSummary(
            @RequestBody FeedbackSummaryDTO feedbackSummaryDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            FeedbackSummary feedbackSummary = feedbackSummaryService.createSummary(feedbackSummaryDTO);
            return ResponseEntity.ok(feedbackSummary);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getAllFeedbackOfRoom(@PathVariable("roomId") Long roomId){
        try{
            FeedbackSummaryResponse feedbackSummaryList = feedbackSummaryService.getFeedbackOfRoom(roomId);
            return ResponseEntity.ok(feedbackSummaryList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
