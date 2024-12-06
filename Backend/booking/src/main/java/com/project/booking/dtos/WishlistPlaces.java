package com.project.booking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistPlaces {
    @JsonProperty("wishlist_id")
    private Long wishlistId;

    @JsonProperty("room_id")
    private Long roomId;
}
