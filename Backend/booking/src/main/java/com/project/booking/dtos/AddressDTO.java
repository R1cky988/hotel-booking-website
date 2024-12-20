package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @JsonProperty("full_address")
    private String full;

    @JsonProperty("postal_code")
    private String postalCode;

    private String street;

    private String country;

    private String region;
}
