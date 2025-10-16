package com.cts.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "Customer ID must not be null")
    @Min(value = 1, message = "Customer ID must be a positive integer")
    private int customerId;

    @NotNull(message = "Vehicle ID must not be null")
    @Min(value = 1, message = "Vehicle ID must be a positive integer")
    private Integer vehicleId;

    @NotNull(message = "Center ID must not be null")
    @Min(value = 1, message = "Center ID must be a positive integer")
    private Integer centerId;

    @NotNull(message = "Booking Date must not be null")
    @FutureOrPresent(message = "Booking Date must be today or a future date")
    private LocalDateTime bookingDate;
        
    @NotBlank(message = "Timeslot must not be blank")
    @Size(min = 5, max = 10, message = "Timeslot must be between 5 and 10 characters")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9](-(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])?$",
             message = "Timeslot format is invalid. Use a valid time format like HH:MM or HH:MM-HH:MM")
    private String timeslot;
}