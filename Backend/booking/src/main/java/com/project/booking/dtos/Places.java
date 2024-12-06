package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Places {
    private String name;

    @NotNull
    @JsonProperty("room_type_id")
    private Long roomTypeId;

    @NotBlank
    private String description;

    @NotBlank
    @JsonProperty("owner_name")
    private String ownerName;

    @NotBlank
    @JsonProperty("owner_phone")
    private String ownerPhone;

    private int ranking;

    private String type;
}
