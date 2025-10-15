package com.cts.service;

import java.util.List;
import com.cts.dto.VehicleRequestDTO;
import com.cts.entity.Vehicles;

public interface VehicleService {

    Vehicles registerVehicle(VehicleRequestDTO vehicleDto, int id);
//    Vehicles registerVehicle(VehicleRequestDTO vehicleDto, String email);
    Vehicles updateVehicle(int customerId, Integer id, VehicleRequestDTO vehicleDto);
//    Vehicles updateVehicle(String email, Integer id, VehicleRequestDTO vehicleDto);
    Vehicles viewVehicle(int customerId, Integer id);
    List<Vehicles> getAllVehiclesById(int customerId);
//    List<Vehicles> getAllVehiclesByEmail(String email);
//	List<Vehicles> getAllVehicles(String email);
}