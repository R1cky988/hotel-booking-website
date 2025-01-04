package com.project.booking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hotels")
public class Hotel extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
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
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference//Đánh dấu mối quan h từ phía cha
    private List<HotelImage> images;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<HotelFacility> facilities;


}
