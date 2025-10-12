package com.cts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    private String userEmail;
    private Integer vehicleId;
    private Integer centerId;
    
    private LocalDateTime bookingDate; 
    private String timeslot;
}