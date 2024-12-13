package com.project.booking.repositories;

import com.project.booking.models.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    @Query("SELECT rd FROM RoomDetail rd WHERE rd.place.id = :placeId")
    List<RoomDetail> findAllRoomInPlace(@Param("placeId") Long placeId);
}
