package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailDTO {

    @NotNull
    @JsonProperty("room_id")
    private Long roomId;

    @NotNull
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


}
