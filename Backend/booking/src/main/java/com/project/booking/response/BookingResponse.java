package com.project.booking.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.models.BookingDetail;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;

    @JsonProperty("room_name")
    private String roomId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("check_in")
    private String checkIn;

    @JsonProperty("check_out")
    private String checkOut;

    private String name;

    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("special_request")
    private String specialRequest;

    @JsonProperty("total_price")
    private String totalPrice;

    private Long totalRoom;

    private String roomName;

    static NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static BookingResponse fromBooking(BookingDetail bookingDetail, Long totalPrice){
        BookingResponse bookingRespone =  BookingResponse.builder()
                .id(bookingDetail.getId())
                .userId(bookingDetail.getUserId().getId())
                .roomId(bookingDetail.getRoomId().getRoomName())
                .checkIn(bookingDetail.getCheckIn().toString())
                .checkOut(bookingDetail.getCheckOut().toString())
                .name(bookingDetail.getName())
                .phone(bookingDetail.getPhone())
                .email(bookingDetail.getEmail())
                .specialRequest(bookingDetail.getSpecialRequest())
                .roomName(bookingDetail.getRoomName())
                .totalRoom(bookingDetail.getNumberOfRoom())
                .build();
        bookingRespone.setTotalPrice(formatter.format(totalPrice));
        return bookingRespone;
    }
}
