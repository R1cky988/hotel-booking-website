package com.project.booking.repositories;

import com.project.booking.models.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    @Query("SELECT DATEDIFF(bd.checkOut, bd.checkIn) FROM BookingDetail bd WHERE bd.id = :id")
    Long getDateDiffById(@Param("id") Long id);
}
