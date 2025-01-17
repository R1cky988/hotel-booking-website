package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailDTO {

    @NotNull(message = "room id must not be null")
    @JsonProperty("room_id")
    private Long roomId;

    @NotNull(message = "user id must not be null")
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("check_in")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @JsonProperty("check_out")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    private String name;

    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("special_request")
    private String specialRequest;

    private String roomName;

    private Long requireRoom;


}
