package com.cts.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	
}