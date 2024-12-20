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
public class FacilityDTO {
    private String name;

    private String overview;

    @JsonProperty("facility_details")
    private List<FacilityDetailDTO> facilityDetails;
}
