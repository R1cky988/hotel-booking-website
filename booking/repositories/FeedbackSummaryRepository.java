package com.project.booking.repositories;

import com.project.booking.models.FeedbackSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackSummaryRepository extends JpaRepository<FeedbackSummary, Long> {
    @Query("SELECT fs FROM FeedbackSummary fs WHERE fs.hotel.id = :hotelId")
    FeedbackSummary findByHotelId(@Param("hotelId") Long hotelId);

    List<FeedbackSummary> findAllByHotelId(Long hotelId);
}
