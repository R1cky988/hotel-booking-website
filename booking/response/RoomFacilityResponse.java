package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.dtos.RoomFacilityDetailDTO;
import com.project.booking.models.RoomFacilityDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomFacilityResponse {
    private String name;

    private String overview;

    @JsonProperty("room_facility_details")
    private List<RoomFacilityDetail> roomFacilityDetail;
}
