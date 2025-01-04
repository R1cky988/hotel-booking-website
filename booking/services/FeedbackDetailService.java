package com.project.booking.services;

import com.project.booking.dtos.FeedbackDetailDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.Users;
import com.project.booking.repositories.FeedbackDetailRepository;
import com.project.booking.repositories.FeedbackSummaryRepository;
import com.project.booking.repositories.UsersRepository;
import com.project.booking.response.FeedbackDetailResponse;
import com.project.booking.response.FeedbackSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackDetailService {
    private final FeedbackDetailRepository feedbackDetailRepository;
    private final FeedbackSummaryRepository feedbackSummaryRepository;
    private final UsersRepository usersRepository;


    public FeedbackDetail createFeedback(FeedbackDetailDTO feedbackDetailDTO){
        FeedbackSummary existingSummary = feedbackSummaryRepository.
                findById(feedbackDetailDTO.getFeedbackSummaryId())
                .orElseThrow(()->new DataNotFoundException("Cannot find"));
        Users users = usersRepository.findById(feedbackDetailDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot find user"));

        FeedbackDetail newFeedback = FeedbackDetail.builder()
                .feedbackSummary(existingSummary)
                .rate(feedbackDetailDTO.getRating())
                .name(feedbackDetailDTO.getName())
                .comment(feedbackDetailDTO.getComment())
                .userId(users)
                .build();
        return feedbackDetailRepository.save(newFeedback);
    }

    public List<FeedbackDetail> getFeedbackDetailOfSummary(Long feedbackSummaryId){
        return feedbackDetailRepository.findAllFeedbackOfSummary(feedbackSummaryId);
    }
    // Phương thức để lấy tất cả feedback
    public List<FeedbackDetail> getAllFeedbackDetails() {
        return feedbackDetailRepository.findAllFeedbackDetails();
    }
    public FeedbackDetailResponse getFeedbackDetailById(Long feedbackId) {
        FeedbackDetail feedbackDetail =  feedbackDetailRepository.findById(feedbackId)
                .orElseThrow(()-> new DataNotFoundException("cannot"));
        return FeedbackDetailResponse.fromDetail(feedbackDetail);
    }

}
