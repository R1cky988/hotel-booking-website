package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.booking.models.FeedbackDetail;
import com.project.booking.models.FeedbackSummary;
import com.project.booking.models.Hotel;
import com.project.booking.models.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDetailResponse {

    private Long id;

    private String room;

    private String name;

    private Long userId;

    private String comment;

    private int rate;

    private String email;

    private LocalDate createTime;

    private String roomName;
    public static FeedbackDetailResponse fromDetail(FeedbackDetail feedbackDetail){
        FeedbackDetailResponse feedbackDetailResponse = FeedbackDetailResponse.builder()
                .id(feedbackDetail.getId())
                .room(feedbackDetail.getRoomName())
                .roomName(feedbackDetail.getRoomName())
                .name(feedbackDetail.getName())
                .email(feedbackDetail.getEmail())
                .userId(feedbackDetail.getUserId().getId())
                .comment(feedbackDetail.getComment())
                .rate(feedbackDetail.getRate())
                .build();
        feedbackDetailResponse.setCreateTime(LocalDate.now());
        return feedbackDetailResponse;
    }
}
