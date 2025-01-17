package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomFacilityDetailDTO {
    private String name;

    @JsonProperty("additional_info")
    private String additionalInfo;
}
