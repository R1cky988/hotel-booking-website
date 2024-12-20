package com.project.booking.repositories;

import com.project.booking.models.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {
    @Query("SELECT hi.imageUrl FROM HotelImage hi WHERE hi.hotel.id = :hotelId")
    List<String> getHotelImage(@Param("hotelId") Long hotelId);
}
