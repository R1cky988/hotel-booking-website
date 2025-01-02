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
@Table(name = "feedbacksummaries")
public class FeedbackSummary extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "rating")
    private double rate;

    @OneToMany(mappedBy = "feedbackSummary", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<FeedbackDetail> feedbackDetails;
}
