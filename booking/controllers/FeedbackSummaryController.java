//package com.project.booking.controllers;
//
//import com.project.booking.dtos.FeedbackSummaryDTO;
//import com.project.booking.models.FeedbackDetail;
//import com.project.booking.models.FeedbackSummary;
//import com.project.booking.response.FeedbackSummaryResponse;
//import com.project.booking.services.FeedbackSummaryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/summary")
//@RequiredArgsConstructor
//public class FeedbackSummaryController {
//    private final FeedbackSummaryService feedbackSummaryService;
//
//    @PostMapping("")
//    public ResponseEntity<?> createSummary(
//            @RequestBody FeedbackSummaryDTO feedbackSummaryDTO,
//            BindingResult result
//    ){
//        try{
//            if(result.hasErrors()){
//                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            FeedbackSummary feedbackSummary = feedbackSummaryService.createSummary(feedbackSummaryDTO);
//            return ResponseEntity.ok(feedbackSummary);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/room/{roomId}")
//    public ResponseEntity<?> getAllFeedbackOfRoom(@PathVariable("roomId") Long roomId){
//        try{
//            FeedbackSummaryResponse feedbackSummaryList = feedbackSummaryService.getFeedbackOfRoom(roomId);
//            return ResponseEntity.ok(feedbackSummaryList);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
////@GetMapping("/room/{roomId}")
////public String getAllFeedbackOfRoom(@PathVariable("roomId") Long roomId, Model model) {
////    try {
////        FeedbackSummaryResponse feedbackSummary = feedbackSummaryService.getFeedbackOfRoom(roomId);
////        System.out.println("Feedback Details: " + feedbackSummary.getFeedbackDetails());
////        model.addAttribute("feedbackSummary", feedbackSummary);
////        return "House"; // Tên file HTML
////    } catch (Exception e) {
////        model.addAttribute("error", e.getMessage());
////        return "errorPage"; // Trang lỗi
////    }
////}
//
//}

package com.project.booking.controllers;

import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.Hotel;
import com.project.booking.services.FeedbackSummaryService;
import com.project.booking.response.FeedbackSummaryResponse;
import com.project.booking.services.FeedbackDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FeedbackSummaryController {


    private final FeedbackSummaryService feedbackSummaryService;
    private final FeedbackDetailController feedbackDetailService;
    private final HotelController hotelController;

    // Phương thức để hiển thị feedback của 1 hotel
//    @GetMapping("/hotel/{hotelId}/feedback")
//    public String getFeedbackOfHotel(@PathVariable Long hotelId, Model model) {
//        // Lấy thông tin feedback từ service
//        FeedbackSummaryResponse feedbackSummaryResponse = feedbackSummaryService.getFeedbackOfHotel(hotelId);
//
//        // Thêm thông tin vào model để Thymeleaf có thể sử dụng
//        model.addAttribute("feedbackSummary", feedbackSummaryResponse);
//
//        // Trả về view tên là 'roomFeedback' (roomFeedback.html)
//        return "index";
//    }
    // đường dẫn trang index
    // lấy tất cả fbsummary, fbdetails
    @GetMapping("/index")
    public String getAllFeedback(Model model) {
        // Lấy tất cả feedback từ service
        List<FeedbackSummaryResponse> feedbackSummaryList = feedbackSummaryService.getAllFeedback();
        // Thêm danh sách feedback vào model để Thymeleaf có thể sử dụng
        model.addAttribute("feedbackSummaryList", feedbackSummaryList);

        //lấy all feedback của web
        List<FeedbackDetail> feedbackDetails = feedbackDetailService.getAllFeedbackDetails();
        // Thêm vào model để Thymeleaf có thể sử dụng
        model.addAttribute("feedbackDetails", feedbackDetails);

        List<Hotel> hotels = hotelController.getHotels();
        // Truyền dữ liệu vào model để hiển thị trên giao diện
        model.addAttribute("hotels", hotels);
        // Trả về view 'index.html'
        return "index";
    }
    @GetMapping("/index/search")
    public String searchHotels(@RequestParam(required = false) String type,
                               @RequestParam(required = false) String region,
                               @RequestParam(required = false) String country,
                               Model model) {
        // Nếu region hoặc country là null, không truy vấn hoặc truyền giá trị mặc định
        if (region == null) {
            region = ""; // Hoặc có thể bỏ qua, tùy yêu cầu
        }

        if (country == null) {
            country = ""; // Hoặc bỏ qua nếu không cần thiết
        }
        List<Hotel> hotels = hotelController.searchHotels(type, region, country);
        model.addAttribute("hotels", hotels);
        return "search-results"; // Tên view trả về danh sách kết quả
    }


}

