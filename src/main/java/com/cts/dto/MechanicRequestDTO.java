package com.cts.dto;

import com.cts.entity.Mechanic.Availability;
import com.cts.entity.Mechanic.Rating;
import com.cts.entity.Mechanic.VerificationStatus;
import com.cts.entity.Mechanic.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
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
public class MechanicRequestDTO {

	@NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotNull(message = "Center ID is required")
    @Min(value = 1, message = "Center ID must be a positive integer")
    private Integer centerId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Expertise is required")
    @Size(max = 100, message = "Expertise description cannot exceed 100 characters")
    private String expertise;

    @NotBlank(message = "Skills are required")
    @Size(max = 500, message = "Skills description cannot exceed 500 characters")
    private String skills;

    @NotNull(message = "Availability status is required")
    private Availability availability;

    @NotNull(message = "Rating is required")
    private Rating rating;

    @NotNull(message = "Verification Status is required")
    private VerificationStatus isVerified;

    @NotNull(message = "Status is required")
    private Status status;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;
}