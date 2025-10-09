package com.cts.dto;

import com.cts.entity.Vehicles.VehicleType;
import lombok.Data;

@Data
public class VehicleResponseDTO {
    private Integer vehicleId;
    private String email;
    private String make;
    private String model;
    private Integer year;
    private String registrationNumber;
    private VehicleType vehicleType;
    private String engine;
    private String abs;
    private String doors;
    private String ac;
    private String transmission;
    private String fuel;
}