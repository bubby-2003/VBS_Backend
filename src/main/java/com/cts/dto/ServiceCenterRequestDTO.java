package com.cts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterRequestDTO {
	@NotBlank
    private String name;
	@NotBlank
    private String location;
	@NotBlank
	@Pattern(regexp = "^[1-9]\\d{9}$", message = "Mobile number must be 10 digits and not start with 0")
	private String contact;

    
}