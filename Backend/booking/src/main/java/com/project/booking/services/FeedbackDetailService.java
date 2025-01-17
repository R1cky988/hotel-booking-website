package com.project.booking.services;

import com.project.booking.dtos.FeedbackDetailDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.Users;
import com.project.booking.repositories.FeedbackDetailRepository;
import com.project.booking.repositories.FeedbackSummaryRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.repositories.UsersRepository;
import com.project.booking.response.FeedbackDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackDetailService {
    private final FeedbackDetailRepository feedbackDetailRepository;
    private final FeedbackSummaryRepository feedbackSummaryRepository;
    private final UsersRepository usersRepository;
    private final RoomDetailRepository roomDetailRepository;

    public FeedbackDetail createFeedback(FeedbackDetailDTO feedbackDetailDTO){
        FeedbackSummary existingSummary = feedbackSummaryRepository.
                findById(feedbackDetailDTO.getFeedbackSummaryId())
                .orElseThrow(()->new DataNotFoundException("Cannot find"));
        Users users = usersRepository.findById(feedbackDetailDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find user"));

        FeedbackDetail newFeedback = FeedbackDetail.builder()
                .feedbackSummary(existingSummary)
                .roomName(feedbackDetailDTO.getRoomName())
                .rate(feedbackDetailDTO.getRating())
                .name(feedbackDetailDTO.getName())
                .email(feedbackDetailDTO.getEmail())
                .comment(feedbackDetailDTO.getComment())
                .userId(users)
                .build();
        return feedbackDetailRepository.save(newFeedback);
    }
    public List<String> getRoomsByHotelId(Long hotelId) {
        return roomDetailRepository.findRoomNamesByHotelId(hotelId);
    }
    public List<FeedbackDetail> getFeedbackDetailOfSummary(Long feedbackSummaryId){
        return feedbackDetailRepository.findAllFeedbackOfSummary(feedbackSummaryId);
    }

    public List<FeedbackDetail> getAllFeedbackDetails() {
        return feedbackDetailRepository.findAllFeedbackDetails();
    }

    public FeedbackDetailResponse getFeedbackDetailById(Long feedbackId) {
        FeedbackDetail feedbackDetail =  feedbackDetailRepository.findById(feedbackId)
                .orElseThrow(()-> new DataNotFoundException("cannot"));
        return FeedbackDetailResponse.fromDetail(feedbackDetail);
    }

}
