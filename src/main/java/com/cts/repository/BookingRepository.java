package com.cts.repository;

import com.cts.entity.Booking;
import com.cts.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	List<Booking> findByMechanicAndBookingId(Mechanic mechanic, Integer bookingId);

    @Query("SELECT b.feedback FROM Booking b WHERE b.mechanic.id = :mechanicId AND b.feedback IS NOT NULL")
    List<String> findFeedbackByMechanic(Integer mechanicId);
}
 