package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.RoomDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSummaryResponse {

    private Long id;


    private Long roomId;


    private double rating;

    private List<FeedbackDetailResponse> feedbackDetails;

    public static FeedbackSummaryResponse fromSummary(FeedbackSummary feedbackSummary){
        List<FeedbackDetail> feedbackDetail = feedbackSummary.getFeedbackDetails();

        double averageRating = feedbackDetail.stream().mapToInt(FeedbackDetail::getRate)
                .average().orElse(0.0);

        List<FeedbackDetailResponse> feedbackDetailResponses = feedbackDetail.stream().map(FeedbackDetailResponse::fromDetail)
                .toList();

        FeedbackSummaryResponse feedbackSummaryResponse = FeedbackSummaryResponse.builder()
                .id(feedbackSummary.getId())
                .roomId(feedbackSummary.getRoom().getId())
                .rating(averageRating)
                .feedbackDetails(feedbackDetailResponses)
                .build();

        return feedbackSummaryResponse;
    }
}
