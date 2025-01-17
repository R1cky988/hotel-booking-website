package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.booking.models.Address;
import com.project.booking.models.HotelImage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private String name;
    private String type;

    private String description;

    private Long stars;
    private Double price;
    private Float rating;
    private Long reviews;
    private Double latitude;
    private Double longitude;
    private String image;

    @JsonProperty("address")
    private AddressDTO addressDTO;

    private List<HotelImage> images;
    @JsonProperty("facilities")
    private List<FacilityDTO> facilityDTO;
}
