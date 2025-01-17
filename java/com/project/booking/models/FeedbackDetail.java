package com.project.booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.booking.dtos.FeedbackSummaryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "feedbackdetails")
public class FeedbackDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feedback_summary_id")
    @JsonBackReference
    private FeedbackSummary feedbackSummary;

    @Column(name = "name")
    private String name;

    @Column(name = "room_name")
    private String roomName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rate")
    private int rate;

    private String email;

    @Column(name = "create_time")
    private LocalDate createTime;
    @PrePersist
    protected void onCreate(){
        createTime = LocalDate.now();
    }

}
