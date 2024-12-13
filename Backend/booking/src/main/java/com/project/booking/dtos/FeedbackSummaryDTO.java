package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackSummaryDTO {
    @NotNull
    @JsonProperty("room_id")
    private Long roomId;

    private Long rating;

}
