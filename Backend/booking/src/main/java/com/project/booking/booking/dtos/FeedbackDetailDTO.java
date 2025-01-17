package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.models.Hotel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotNull(message = "feedback summary id cannot null")
    @JsonProperty("feedback_summary_id")
    private Long feedbackSummaryId;

    private String roomName;

    @NotBlank(message = "name cannot null")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "user id cannot null")
    @JsonProperty("user_id")
    private Long userId;

    private String comment;

    @Min(1)
    @Max(5)
    private int rating;

    private String email;

}
