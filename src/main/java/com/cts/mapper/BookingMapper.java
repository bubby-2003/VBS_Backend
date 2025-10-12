package com.cts.mapper;

import com.cts.dto.BookingRequestDTO;
import com.cts.dto.BookingResponseDTO;
import com.cts.entity.Booking;

public class BookingMapper {
    public static BookingResponseDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setTimeslot(booking.getTimeslot());
        dto.setFeedback(booking.getFeedback());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setIsVerified(booking.getIsVerified());
        dto.setStatus(booking.getStatus());

        if (booking.getUser() != null) {
            dto.setUserEmail(booking.getUser().getAuth().getEmail());
        }
        if (booking.getVehicle() != null) {
            dto.setVehicleId(booking.getVehicle().getVehicleId());
        }
        if (booking.getServiceCenter() != null) {
            dto.setCenterId(booking.getServiceCenter().getServicecenterId());
        }
        if (booking.getMechanic() != null) {
            dto.setMechanicEmail(booking.getMechanic().getAuth().getEmail());
        }

        return dto;
    }
    public static Booking toEntity(BookingRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setTimeslot(dto.getTimeslot()); 
        
        return booking;
    }
}