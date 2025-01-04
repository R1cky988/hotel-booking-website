package com.project.booking.controllers;

import com.project.booking.dtos.FeedbackDetailDTO;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.response.FeedbackDetailResponse;
import com.project.booking.services.FeedbackDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackDetailController {
    public final FeedbackDetailService feedbackDetailService;

    @PostMapping("")
    public ResponseEntity<?> createFeedback(
            @Valid @RequestBody FeedbackDetailDTO feedbackDetailDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            FeedbackDetail feedbackDetail = feedbackDetailService.createFeedback(feedbackDetailDTO);
            return ResponseEntity.ok(feedbackDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //    @GetMapping("")
// GET: Lấy tất cả feedback details của một feedbackSummaryId và trả về view với Thymeleaf
    @GetMapping("/house/{feedbackSummaryId}")
    public String getAllFeedbackDetails(@PathVariable Long feedbackSummaryId, Model model) {
        List<FeedbackDetailResponse> feedbackDetailResponses = feedbackDetailService
                .getFeedbackDetailOfSummary(feedbackSummaryId)
                .stream()
                .map(FeedbackDetailResponse::fromDetail)
                .collect(Collectors.toList());

        model.addAttribute("feedbackDetails", feedbackDetailResponses);
        return "House"; // Trả về view "feedbackDetail"
    }

    @GetMapping("/house")
    public String getFeedbackDetail(@RequestParam("id") Long feedbackId, Model model) {
        // Lấy thông tin chi tiết của feedback dựa trên ID

        FeedbackDetailResponse feedbackDetailResponse = feedbackDetailService.getFeedbackDetailById(feedbackId);

        // Thêm thông tin vào model
        model.addAttribute("feedbackDetail", feedbackDetailResponse);

        // Trả về view modal
        return "FeedbackDetail";
    }


    //    @GetMapping("/details")
//    public String getAllFeedbackDetails(Model model) {
//        // Lấy tất cả feedback từ service
//        List<FeedbackDetail> feedbackDetails = feedbackDetailService.getAllFeedbackDetails();
//
//        // Thêm vào model để Thymeleaf có thể sử dụng
//        model.addAttribute("feedbackDetails", feedbackDetails);
//
//        // Trả về view 'index.html'
//        return "index";  // Tên view Thymeleaf bạn sẽ sử dụng
//    }
    @ResponseBody  // Trả về danh sách FeedbackDetail dưới dạng JSON
    public List<FeedbackDetail> getAllFeedbackDetails() {
        // Lấy tất cả feedback từ service
        return feedbackDetailService.getAllFeedbackDetails();
    }


}
