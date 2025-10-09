package com.cts.mapper;

import com.cts.dto.VehicleResponseDTO;
import com.cts.entity.Vehicles;

public class VehicleMapper {

    public static VehicleResponseDTO toDTO(Vehicles vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setEmail(vehicle.getUser().getAuth().getEmail());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setRegistrationNumber(vehicle.getRegistrationNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setEngine(vehicle.getEngine());
        dto.setAbs(vehicle.getAbs());
        dto.setDoors(vehicle.getDoors());
        dto.setAc(vehicle.getAc());
        dto.setTransmission(vehicle.getTransmission());
        dto.setFuel(vehicle.getFuel());
        return dto;
    }
}