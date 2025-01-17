package com.project.booking.repositories;

import com.project.booking.models.HotelFacilityDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelFacilityDetailRepository extends JpaRepository<HotelFacilityDetail, Long> {
}
