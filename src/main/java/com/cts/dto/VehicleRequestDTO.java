package com.cts.dto;

import com.cts.entity.Vehicles.VehicleType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class VehicleRequestDTO {

	@NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotBlank(message = "Vehicle make is required")
    @Size(max = 50, message = "Make cannot exceed 50 characters")
    private String make;

    @NotBlank(message = "Vehicle model is required")
    @Size(max = 50, message = "Model cannot exceed 50 characters")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be 1900 or later")
    @Max(value = 2030, message = "Year cannot be in the distant future")
    private Integer year;

    @NotBlank(message = "Registration number is required")
    @Pattern(regexp = "^[A-Z0-9\\s]{4,15}$", message = "Registration number format is invalid")
    private String registrationNumber;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    @NotBlank(message = "Engine details are required")
    @Size(max = 50, message = "Engine details cannot exceed 50 characters")
    private String engine;

    @NotBlank(message = "ABS status is required")
    @Pattern(regexp = "^(Yes|No)$", message = "ABS status must be 'Yes' or 'No'")
    private String abs;

    @NotBlank(message = "Number of doors is required")
    @Pattern(regexp = "^4$", message = "Doors must be exactly 4")
    private String doors;

    @NotBlank(message = "AC status is required")
    @Pattern(regexp = "^(Yes|No)$", message = "AC status must be 'Yes' or 'No'")
    private String ac;

    @NotBlank(message = "Transmission type is required")
    @Pattern(regexp = "^(Automatic|Manual)$", message = "Transmission must be Automatic or Manual")
    private String transmission;

    @NotBlank(message = "Fuel type is required")
    @Pattern(regexp = "^(Petrol|Diesel|Electric)$", message = "Fuel type is invalid")
    private String fuel;
}