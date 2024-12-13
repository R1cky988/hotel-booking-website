package com.project.booking.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roomdetails")
public class RoomDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "perks")
    private String perks;

    @Column(name = "extra_info")
    private String extraInfo;

    @Column(name = "check_in")
    private Date checkIn;

    @Column(name = "check_out")
    private Date checkOut;

    @Column(name = "max_guests")
    private int maxGuests;

    @Column(name = "price")
    private int price;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
