package com.project.booking.models;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "room_id")
    private RoomDetail room;

    @Column(name = "rating")
    private double rate;
}
