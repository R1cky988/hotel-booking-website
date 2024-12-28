package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.models.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailDTO {
    private Boolean available;

    private String url;

    @JsonProperty("room_type")
    private String roomType;

    private int persons;

    @JsonProperty("check_in")
    private Date checkIn;

    @JsonProperty("check_out")
    private Date checkOut;

    @JsonProperty("price_per_night")
    private Long pricePerNight;

    @JsonProperty("hotel_id")
    private Long hotelId;
    @JsonProperty("facilities")
    private List<RoomFacilityDTO> facilityDTO;

}
