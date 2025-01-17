package com.project.booking.models;

import com.project.booking.dtos.RoomDetailDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wishlistplaces")
public class WishlistPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlistId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomDetail roomId;
}
