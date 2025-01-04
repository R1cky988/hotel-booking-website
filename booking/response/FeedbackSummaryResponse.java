package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.booking.models.Address;
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
    private Long hotelId;
    private String hotelName;
    private String hotelImage;
    private String region;
    private String country;

    private double rating;
    private Long reviews;
    private List<FeedbackDetailResponse> feedbackDetails;

    public static FeedbackSummaryResponse fromSummary(FeedbackSummary feedbackSummary){
        List<FeedbackDetail> feedbackDetail = feedbackSummary.getFeedbackDetails();

        double averageRating = feedbackDetail.stream().mapToInt(FeedbackDetail::getRate)
                .average().orElse(0.0);

        List<FeedbackDetailResponse> feedbackDetailResponses = feedbackDetail.stream().map(FeedbackDetailResponse::fromDetail)
                .toList();
        Address address = feedbackSummary.getHotel().getAddress();

        FeedbackSummaryResponse feedbackSummaryResponse = FeedbackSummaryResponse.builder()
                .id(feedbackSummary.getId())
                .hotelId(feedbackSummary.getHotel().getId())
                .hotelName(feedbackSummary.getHotel().getName())
                .hotelImage(feedbackSummary.getHotel().getImage())
                .region(address.getRegion()) // Lấy region từ Address
                .country(address.getCountry())
                .rating(averageRating)
                .reviews(feedbackSummary.getHotel().getReviews())
                .feedbackDetails(feedbackDetailResponses)
                .build();

        return feedbackSummaryResponse;
    }
}
