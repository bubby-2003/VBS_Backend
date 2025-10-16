package com.cts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterRequestDTO {

    @NotBlank(message = "Service Center name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Location/Address is required")
    @Size(min = 5, max = 300, message = "Location must be between 5 and 300 characters")
    private String location;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[1-9]\\d{9}$", message = "Mobile number must be a 10-digit number and cannot start with 0")
    private String contact;
}