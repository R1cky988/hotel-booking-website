package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.dtos.RoomFacilityDTO;
import com.project.booking.models.RoomDetail;
import com.project.booking.models.RoomFacilities;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailResponse {
    private Long id;

    private Long available;

    private String url;

    @JsonProperty("room_type")
    private String roomType;

    private int persons;

    @JsonProperty("check_in")
    private Date checkIn;

    @JsonProperty("check_out")
    private Date checkOut;

    @JsonProperty("hotel_id")
    private Long hotelId;

    @JsonProperty("price_per_night")
    private Long pricePerNight;

    @JsonProperty("facilities")
    private List<RoomFacilities> facility;

    public static RoomDetailResponse fromRoomDetail(RoomDetail roomDetail){
        RoomDetailResponse roomDetailResponse = RoomDetailResponse.builder()
                .id(roomDetail.getId())
                .available(roomDetail.getAvailable())
                .url(roomDetail.getUrl())
                .roomType(roomDetail.getRoomType())
                .persons(roomDetail.getPersons())
                .checkIn(roomDetail.getCheckIn())
                .checkOut(roomDetail.getCheckOut())
                .hotelId(roomDetail.getHotel().getId())
                .pricePerNight(roomDetail.getPricePerNight())
                .facility(roomDetail.getFacilities())
                .build();
        return roomDetailResponse;
    }
}
