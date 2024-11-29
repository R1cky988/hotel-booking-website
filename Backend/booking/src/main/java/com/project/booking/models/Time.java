package com.project.booking.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Time {
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate(){
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updateTime = LocalDateTime.now();
    }
}
