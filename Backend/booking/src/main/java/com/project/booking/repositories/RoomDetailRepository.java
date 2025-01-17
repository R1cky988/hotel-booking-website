package com.project.booking.repositories;

import com.project.booking.models.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    @Query("SELECT rd FROM RoomDetail rd WHERE rd.hotel.id = :hotelId")
    List<RoomDetail> findAllRoomInPlace(@Param("hotelId") Long hotelId);

    @Query("SELECT rd FROM RoomDetail rd WHERE rd.hotel.id = :hotelId " +
            "AND rd.available > 0 " +
            "AND NOT (rd.checkIn >= :checkOutDate OR rd.checkOut <= :checkInDate)")

    List<RoomDetail> findAvailableRooms(
            @Param("hotelId") Long hotelId,
            @Param("checkInDate") Date checkInDate,
            @Param("checkOutDate") Date checkOutDate
    );

    @Query("SELECT rd.roomName FROM RoomDetail rd WHERE rd.hotel.id = :hotelId")
    List<String> findRoomNamesByHotelId(@Param("hotelId") Long hotelId);

    @Query("SELECT rd FROM RoomDetail rd WHERE rd.hotel.id = :hotelId")
    List<RoomDetail> findRoomByHotelId(@Param("hotelId") Long hotelId);

}
