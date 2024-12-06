package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @JsonProperty("owner_id")
    private Long ownerId;

    private int deleted;

}
