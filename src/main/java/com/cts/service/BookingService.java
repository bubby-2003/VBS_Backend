package com.cts.service;

import com.cts.entity.Booking;

public interface BookingService {

	public Booking createBooking(Booking booking);

	public Booking getBookingDetailsById(Integer id);

	public void cancelBooking(Integer id);
	
}