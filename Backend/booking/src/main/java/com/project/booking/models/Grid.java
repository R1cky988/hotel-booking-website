package com.project.booking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Grid {
    @Column(name = "photo_width")
    private int photoWidth;

    @Column(name = "photo_height")
    private int photoHeight;
}
