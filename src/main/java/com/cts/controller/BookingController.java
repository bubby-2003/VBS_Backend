package com.cts.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.BookingRequestDTO; 
import com.cts.dto.BookingResponseDTO; 
import com.cts.entity.Booking;
import com.cts.mapper.BookingMapper;
import com.cts.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking Management", description = "Create Web Api's for Making Appointments")
@AllArgsConstructor
public class BookingController {

    private BookingService sbook;

    @Operation(
        summary = "Create a new booking",
        description = "Creates a new booking record with the provided booking details"
    )
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBook(@RequestBody BookingRequestDTO bookingDto) {
        Booking booking = sbook.createBooking(bookingDto);
        
        BookingResponseDTO responseDto = BookingMapper.toDTO(booking);
        
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(
        summary = "View booking details",
        description = "Fetches booking details by booking ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> viewBook(@PathVariable Integer id) {
      
         Booking book = sbook.getBookingDetailsById(id);
         
         BookingResponseDTO responseDto = BookingMapper.toDTO(book);
         
         return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(
        summary = "Cancel a booking",
        description = "Cancels a booking by booking ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletBook(@PathVariable Integer id) {
        boolean isCanceled = sbook.cancelBooking(id);
        
        if (isCanceled) {
            return new ResponseEntity<>("Booking with ID " + id + " has been successfully canceled.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Could not cancel booking with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}