package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.models.BookingDetail;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;

    @JsonProperty("room_id")
    private Long roomId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("check_in")
    private Date checkIn;

    @JsonProperty("check_out")
    private Date checkOut;

    private String name;

    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("special_request")
    private String specialRequest;

    @JsonProperty("total_price")
    private Long totalPrice;

    public static BookingResponse fromBooking(BookingDetail bookingDetail, Long totalPrice){
        return BookingResponse.builder()
                .id(bookingDetail.getId())
                .userId(bookingDetail.getUserId().getId())
                .roomId(bookingDetail.getRoomId().getId())
                .checkIn(bookingDetail.getCheckIn())
                .checkOut(bookingDetail.getCheckOut())
                .name(bookingDetail.getName())
                .phone(bookingDetail.getPhone())
                .email(bookingDetail.getEmail())
                .specialRequest(bookingDetail.getSpecialRequest())
                .totalPrice(totalPrice)
                .build();
         //bookingResponse;
    }
}
