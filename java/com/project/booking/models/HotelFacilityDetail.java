package com.project.booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hotel_facility_details")
public class HotelFacilityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    @JsonBackReference
    private HotelFacility hotelFacility;

    private String name;

    @Column(name = "additional_info")
    private String additionalInfo;
}
