package com.project.booking.controllers;

import com.project.booking.dtos.FeedbackSummaryDTO;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.Hotel;
import com.project.booking.response.FeedbackSummaryResponse;
import com.project.booking.services.FeedbackDetailService;
import com.project.booking.services.FeedbackSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/summary")
@RequiredArgsConstructor
public class FeedbackSummaryController {
    private final FeedbackSummaryService feedbackSummaryService;
    public final FeedbackDetailService feedbackDetailService;
    private final HotelController hotelController;
    @PostMapping("/summary")
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

    @GetMapping("/index")
    public String getAllFeedback(Model model) {
        // địa điểm phỏ biến
        List<FeedbackSummaryResponse> feedbackSummaryList = feedbackSummaryService.getAllFeedback();
        model.addAttribute("feedbackSummaryList", feedbackSummaryList);
        // lấy all feedback
        List<FeedbackDetail> feedbackDetails = feedbackDetailService.getAllFeedbackDetails();
        model.addAttribute("feedbackDetails", feedbackDetails);
        // một số khách sạn
        List<Hotel> hotels = hotelController.getHotels();
        model.addAttribute("hotels", hotels);
        return "index";
    }
    @GetMapping("/index/search")
    public String searchHotels(@RequestParam(required = false) String type,
                               @RequestParam(required = false) String region,
                               @RequestParam(required = false) String hotelName,
                               Model model) {
        // Nếu region hoặc country là null, không truy vấn hoặc truyền giá trị mặc định
        if (region == null) {
            region = ""; // Hoặc có thể bỏ qua, tùy yêu cầu
        }

        if (hotelName == null) {
            hotelName = ""; // Hoặc bỏ qua nếu không cần thiết
        }
        List<Hotel> hotels = hotelController.searchHotels(type, region, hotelName);
        model.addAttribute("hotels", hotels);
        return "search-results"; // Tên view trả về danh sách kết quả
    }
}
