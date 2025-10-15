package com.cts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeRequestDTO {
	@NotBlank
    private String name;
	@NotBlank
    private String description;
	@NotBlank
    private Double price;
}