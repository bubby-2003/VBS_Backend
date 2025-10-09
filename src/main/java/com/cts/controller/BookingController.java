package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Booking;
import com.cts.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking Management",description = "Create Web Api's for Making Appointments")
public class BookingController {

    @Autowired
    private BookingService sbook;

    @Operation(
        summary = "Create a new booking",
        description = "Creates a new booking record with the provided booking details"
    )
    @PostMapping
    public ResponseEntity<Booking> createBook(@RequestBody Booking booking) {
    	
        booking = sbook.createBooking(booking);
        return new ResponseEntity<Booking>(booking,HttpStatus.CREATED);
    }

    @Operation(
        summary = "View booking details",
        description = "Fetches booking details by booking ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> viewBook(@PathVariable Integer id) {
         Booking book = sbook.getBookingDetailsById(id);
         return new ResponseEntity<Booking>(book,HttpStatus.OK);
    }

    @Operation(
        summary = "Delete a booking",
        description = "Cancels and deletes a booking by booking ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletBook(@PathVariable Integer id) {
        boolean message = sbook.cancelBooking(id);
        return new ResponseEntity<String>("Booking is deleted", HttpStatus.OK);
    }
}
