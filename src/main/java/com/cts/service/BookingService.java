package com.cts.service;

import com.cts.dto.BookingRequestDTO;
import com.cts.entity.Booking; 

public interface BookingService {
	public Booking createBooking(BookingRequestDTO bookingDto);
	public Booking getBookingDetailsById(int id);
	public boolean cancelBooking(int id);
	
}