package com.project.booking.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomImage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "thumb_url")
        private String thumbUrl;

        @Column(name = "large_url")
        private String largeUrl;


        @Column(name = "created_at")
        private LocalDateTime createTime;

        @ManyToOne
        @JoinColumn(name = "room_id")
        private RoomDetail roomDetail;

        @PrePersist
        protected void onCreate(){
            createTime = LocalDateTime.now();
        }

}
