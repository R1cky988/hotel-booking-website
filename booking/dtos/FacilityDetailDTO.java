package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDetailDTO {

    private String name;

    @JsonProperty("additional_info")
    private String additionalInfo;
}
