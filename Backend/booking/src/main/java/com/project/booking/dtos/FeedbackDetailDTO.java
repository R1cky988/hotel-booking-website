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
public class FeedbackDetailDTO {
    @NotNull
    @JsonProperty("feedback_summary_id")
    private Long feedbackSummaryId;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    private String comment;

    private int rating;
}
