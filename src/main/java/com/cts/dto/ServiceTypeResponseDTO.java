package com.cts.dto;

import com.cts.entity.ServiceType.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeResponseDTO {
    
    private Integer serviceTypeId;
    private String name;
    private String description;
    private Double price;
    private Status status;

    private Integer centerId; 
}