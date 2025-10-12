package com.cts.dto;

import java.time.LocalDateTime;

import com.cts.entity.Booking.Status;
import com.cts.entity.Booking.VerificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    
    private Integer bookingId;

    private String userEmail;
    private Integer vehicleId;
    private Integer centerId;
    private String mechanicEmail;
    
    private LocalDateTime bookingDate;
    private String timeslot;
    private String feedback;

    private LocalDateTime createdAt;
    private VerificationStatus isVerified;
    private Status status;
}