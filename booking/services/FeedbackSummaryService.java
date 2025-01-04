package com.project.booking.services;

import com.project.booking.dtos.FeedbackSummaryDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.Hotel;
import com.project.booking.models.RoomDetail;
import com.project.booking.repositories.FeedbackSummaryRepository;
import com.project.booking.repositories.HotelRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.response.FeedbackSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackSummaryService {
    private final FeedbackSummaryRepository feedbackSummaryRepository;
    private final HotelRepository hotelRepository;
    public FeedbackSummary createSummary(FeedbackSummaryDTO feedbackSummaryDTO){
        Hotel hotel = hotelRepository.findById(feedbackSummaryDTO.getHotelId())
                .orElseThrow(()->new DataNotFoundException("Cannot find hotel"));
        FeedbackSummary feedbackSummary = FeedbackSummary.builder()
                .rate(feedbackSummaryDTO.getRating())
                .hotel(hotel)
                .build();
        return feedbackSummaryRepository.save(feedbackSummary);
    }

    public FeedbackSummaryResponse getFeedbackOfHotel(Long hotelId){
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()->new DataNotFoundException("Cannot find hotel"));
        FeedbackSummary summary = feedbackSummaryRepository.findByHotelId(hotelId);
        return FeedbackSummaryResponse.fromSummary(summary);
    }

    public List<FeedbackSummaryResponse> getAllFeedback() {
        // Lấy tất cả feedback từ repository
        List<FeedbackSummary> feedbackSummaries = feedbackSummaryRepository.findAll();

        // Chuyển đổi FeedbackSummary thành FeedbackSummaryResponse
        return feedbackSummaries.stream()
                .map(FeedbackSummaryResponse::fromSummary)
                .collect(Collectors.toList());
    }


}
