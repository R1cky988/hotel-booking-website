package com.project.booking.repositories;

import com.project.booking.models.BookingDetail;
import com.project.booking.models.FeedbackDetail;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackDetailRepository extends JpaRepository<FeedbackDetail, Long> {

    @Query("SELECT fd FROM FeedbackDetail fd WHERE fd.feedbackSummary.id = :feedbackSummaryId")
    Optional<FeedbackDetail> findAllFeedbackOfSummary(@Param("feedbackSummaryId") Long feedbackSummaryId);
}
