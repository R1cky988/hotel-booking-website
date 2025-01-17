package com.project.booking.repositories;

import com.project.booking.models.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    @Query("SELECT ri.largeUrl FROM RoomImage ri WHERE ri.roomDetail.id = :roomId")
    List<String> getRoomImage(@Param("roomId") Long roomId);
}
