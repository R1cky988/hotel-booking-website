package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailDTO {
    private String title;

    private String address;

    private String description;

    private String perks;

    @JsonProperty("extra_info")
    private String extraInfo;

    @JsonProperty("check_in")
    private Date checkIn;

    @JsonProperty("check_out")
    private Date checkOut;

    @JsonProperty("max_guests")
    private int maxGuests;

    private int price;

    private String photo;

    @JsonProperty("place_id")
    private Long placeId;


}
