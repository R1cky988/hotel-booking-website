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
public class RoomDetail {
    private String title;

    private String address;

    private String description;

    private String perks;

    @JsonProperty("extra_info")
    private String extraInfo;

    private Date checkin;

    private Date checkout;

    @JsonProperty("max_guests")
    private int maxGuests;

    private int price;

    private String photo;

    @JsonProperty("place_id")
    private Long placeId;


}
