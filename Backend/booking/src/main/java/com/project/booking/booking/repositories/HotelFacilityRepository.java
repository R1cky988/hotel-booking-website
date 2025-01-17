package com.project.booking.repositories;

import com.project.booking.models.HotelFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelFacilityRepository extends JpaRepository<HotelFacility, Long> {
    @Query("SELECT hf FROM HotelFacility hf WHERE hf.hotel.id = :hotelId")
    List<HotelFacility> findHotelById(@Param("hotelId") Long hotelId);

}
