package com.project.booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "room_facility_detail")
public class RoomFacilityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_facility_id")
    @JsonBackReference
    private RoomFacilities roomFacility;

    private String name;

    @Column(name = "additional_info")
    private String additionalInfo;
}
