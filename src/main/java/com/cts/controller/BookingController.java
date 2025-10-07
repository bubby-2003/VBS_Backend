package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Booking;
import com.cts.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	@Autowired
	private BookingService sbook;
	
	@PostMapping
	public Booking createBook(@RequestBody Booking booking){
		
		return sbook.createBooking(booking);
	
	}
	
	@GetMapping("/{id}")
	public Booking viewBook(@PathVariable Integer id) {
		
		return sbook.getBookingDetailsById(id);
		
	}
	
	@DeleteMapping("/{id}")
	public String deletBook(@PathVariable Integer id) {
		
		sbook.cancelBooking(id);
		return "deleted successfully"; 
	}
	

}
