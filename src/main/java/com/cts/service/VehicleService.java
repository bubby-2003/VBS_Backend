package com.cts.service;

import java.util.List;
import com.cts.dto.VehicleRequestDTO;
import com.cts.entity.Vehicles;

public interface VehicleService {
    Vehicles registerVehicle(VehicleRequestDTO vehicleDto, String email);
    Vehicles updateVehicle(String email, Integer id, VehicleRequestDTO vehicleDto);
    Vehicles viewVehicle(String email, Integer id);
    List<Vehicles> getAllVehicles(String email);
}