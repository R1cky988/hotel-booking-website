package com.project.booking.repositories;

import com.project.booking.models.RoomFacilityDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilityDetailRepository extends JpaRepository<RoomFacilityDetail, Long> {
}
