package com.cts.dto;

import com.cts.entity.ServiceCenter.Rating;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterResponseDTO {

    private Integer servicecenterId;
    private String name;
    private String location;
    private Long contact;

    private Rating rating;
    private String feedback;
}