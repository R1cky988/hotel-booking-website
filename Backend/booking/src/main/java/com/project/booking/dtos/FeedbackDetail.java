package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDetail {
    @NotNull
    @JsonProperty("feedback_summary_id")
    private Long feedbackSummaryId;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    private Date date;

    private String comment;

    private int rating;
}
