package com.cts.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.Booking;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.BookingRepository;
import com.cts.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingRepository brepo;
	
	public Booking createBooking(Booking booking) {
	
		return brepo.save(booking);
	}

	public Booking getBookingDetailsById(int id){
		Booking book = brepo.findById(id).orElse(null);
		if(book == null) {
			throw new ResourceNotFoundException("Id not found");
		}
		return book;

	}
	
	public boolean cancelBooking(int id) {
		Booking book = brepo.findById(id).orElse(null);
		if(book==null)
			throw new ResourceNotFoundException("Booking "+id+" is not found");
		 brepo.deleteById(id);
		 return true;

	}
	
}
