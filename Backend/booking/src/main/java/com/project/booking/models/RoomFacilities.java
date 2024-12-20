package com.project.booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class RoomFacilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String overview;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private RoomDetail room;

    @OneToMany(mappedBy = "roomFacility", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<RoomFacilityDetail> roomFacilityDetailList;
}
