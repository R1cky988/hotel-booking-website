package com.project.booking.repositories;

import com.project.booking.models.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAll(Pageable pageable);
    @Query("SELECT h FROM Hotel h WHERE " +
            "(:type IS NULL OR h.type LIKE %:type%) AND " +
            "(:region IS NULL OR h.address.region LIKE %:region%) AND " +
            "(:country IS NULL OR h.address.country LIKE %:country%)")
    List<Hotel> searchHotels(@Param("type") String type,
                             @Param("region") String region,
                             @Param("country") String country);
}
