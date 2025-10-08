package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Booking createBook(@RequestBody Booking booking) {
        return sbook.createBooking(booking);
    }

    @Operation(
        summary = "View booking details",
        description = "Fetches booking details by booking ID"
    )
    @GetMapping("/{id}")
    public Booking viewBook(@PathVariable Integer id) {
        return sbook.getBookingDetailsById(id);
    }

    @Operation(
        summary = "Delete a booking",
        description = "Cancels and deletes a booking by booking ID"
    )
    @DeleteMapping("/{id}")
    public String deletBook(@PathVariable Integer id) {
        sbook.cancelBooking(id);
        return "deleted successfully";
    }
}
