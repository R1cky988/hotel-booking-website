package com.project.booking.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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

    private Boolean available;

    private String url;

    @Column(name = "room_type")
    private String roomType;

    private int persons;

    @Column(name = "room_left")
    private int roomLeft;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Properties properties;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    
}
