package com.project.booking.repositories;

import com.project.booking.models.RoomFacilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacilities, Long> {
}
