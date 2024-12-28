package com.project.booking.services;

import com.project.booking.dtos.FeedbackSummaryDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.RoomDetail;
import com.project.booking.repositories.FeedbackSummaryRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.response.FeedbackSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackSummaryService {
    private final FeedbackSummaryRepository feedbackSummaryRepository;
    private final RoomDetailRepository roomDetailRepository;
    public FeedbackSummary createSummary(FeedbackSummaryDTO feedbackSummaryDTO){
        RoomDetail roomDetail = roomDetailRepository.findById(feedbackSummaryDTO.getRoomId())
                .orElseThrow(()->new DataNotFoundException("Cannot find room"));
        FeedbackSummary feedbackSummary = FeedbackSummary.builder()
                .rate(feedbackSummaryDTO.getRating())
                .room(roomDetail)
                .build();
        return feedbackSummaryRepository.save(feedbackSummary);
    }

    public FeedbackSummaryResponse getFeedbackOfRoom(Long roomId){
        RoomDetail roomDetail = roomDetailRepository.findById(roomId)
                .orElseThrow(()->new DataNotFoundException("Cannot find room"));
        FeedbackSummary summary = feedbackSummaryRepository.findByRoomId(roomId);
        return FeedbackSummaryResponse.fromSummary(summary);
    }
}
