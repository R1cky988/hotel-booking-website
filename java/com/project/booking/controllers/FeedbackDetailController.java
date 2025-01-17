package com.project.booking.controllers;


import com.project.booking.dtos.FeedbackDetailDTO;
import com.project.booking.dtos.FeedbackSummaryDTO;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.Users;
import com.project.booking.response.FeedbackDetailResponse;
import com.project.booking.response.FeedbackSummaryResponse;
import com.project.booking.services.FeedbackDetailService;
import com.project.booking.services.FeedbackSummaryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final FeedbackDetailService feedbackDetailService;
    private final FeedbackSummaryService feedbackSummaryService;

    @PostMapping("feedback1")
    public String createFeedback(
            @RequestParam("hotelId") Long hotelId,
            @Valid @ModelAttribute FeedbackDetailDTO feedbackDetailDTO,
            BindingResult result,
            Model model,
            HttpSession httpSession
    ){
        try{
            if(result.hasErrors()){
                model.addAttribute("errors", result.getFieldErrors());
                return "FormFeedback1";
            }
            Users loggedInUser = (Users) httpSession.getAttribute("user");

            FeedbackSummaryResponse feedbackSummary = feedbackSummaryService.getFeedbackOfHotel(hotelId);

            if (feedbackSummary == null) {
                FeedbackSummaryDTO newSummaryDTO = new FeedbackSummaryDTO();
                newSummaryDTO.setHotelId(hotelId);
                newSummaryDTO.setRating(0L); // Giá trị ban đầu
                feedbackSummaryService.createSummary(newSummaryDTO);
                feedbackSummary = feedbackSummaryService.getFeedbackOfHotel(hotelId);
            }

            feedbackDetailDTO.setFeedbackSummaryId(feedbackSummary.getId());
            feedbackDetailDTO.setUserId(loggedInUser.getId());
            //feedbackDetailDTO.setRoomId(8L);

            FeedbackDetail feedbackDetail = feedbackDetailService.createFeedback(feedbackDetailDTO);
            return "redirect:/hotel/" + hotelId;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "FormFeedback1";
        }
    }

    @GetMapping("feedback")
    public String Feedback(
            @RequestParam("hotelId") Long hotelId,
            Model model,
            HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            model.addAttribute("error", "Bạn phải đăng nhập trước khi đánh giá");
            return "redirect:/users/login";
        }

        FeedbackDetailDTO feedbackDetailDTO = new FeedbackDetailDTO();
        Users loggedInUser = (Users) httpSession.getAttribute("user");

        FeedbackSummaryResponse feedbackSummary = feedbackSummaryService.getFeedbackOfHotel(hotelId);

        if (feedbackSummary == null) {
            FeedbackSummaryDTO newSummaryDTO = new FeedbackSummaryDTO();
            newSummaryDTO.setHotelId(hotelId);
            newSummaryDTO.setRating(1L); // Giá trị ban đầu
            feedbackSummaryService.createSummary(newSummaryDTO);
            feedbackSummary = feedbackSummaryService.getFeedbackOfHotel(hotelId);
        }
        System.out.println(feedbackSummary.getHotelId());

        feedbackDetailDTO.setFeedbackSummaryId(feedbackSummary.getId());
        feedbackDetailDTO.setUserId(loggedInUser.getId());

        System.out.println("userId" + feedbackDetailDTO.getUserId());
        System.out.println("feedSum" + feedbackDetailDTO.getFeedbackSummaryId());

        if (feedbackDetailDTO.getRating() < 1) {
            feedbackDetailDTO.setRating(1);  // Default to 1 if not selected
        }

        List<String> roomNameList = feedbackDetailService.getRoomsByHotelId(hotelId);

        model.addAttribute("hotelId", hotelId);
        model.addAttribute("roomNames", roomNameList);
        model.addAttribute("feedbackDetailDTO", feedbackDetailDTO);
        return "FormFeedback1";
    }
    // GET: Lấy tất cả feedback details của một feedbackSummaryId và trả về view với Thymeleaf
//    @GetMapping("/house/{hotelId}")
//    public String getAllFeedbackDetails(@PathVariable Long hotelId, Model model) {
//        FeedbackSummaryResponse feedbackSummary = feedbackSummaryService.getFeedbackOfHotel(hotelId);
//        List<FeedbackDetailResponse> feedbackDetailResponses = feedbackDetailService
//                .getFeedbackDetailOfSummary(feedbackSummary.id)
//                .stream()
//                .map(FeedbackDetailResponse::fromDetail)
//                .collect(Collectors.toList());
//
//        model.addAttribute("feedbackDetails", feedbackDetailResponses);
//        return "House"; // Trả về view "feedbackDetail"
//    }

}
