package com.cts.service;

import com.cts.dto.VehicleRequestDTO;
import com.cts.dto.VehicleResponseDTO;

import java.util.List;

public interface VehicleService {

    VehicleResponseDTO registerVehicle(VehicleRequestDTO vehicleDto, int customerId);

    VehicleResponseDTO updateVehicle(int customerId, Integer vehicleId, VehicleRequestDTO vehicleDto);

    VehicleResponseDTO viewVehicle(int customerId, Integer vehicleId);

    List<VehicleResponseDTO> getAllVehiclesById(int customerId);
}
