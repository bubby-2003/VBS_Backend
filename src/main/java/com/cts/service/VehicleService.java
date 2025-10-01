package com.cts.service;
 
import com.cts.entity.Vehicles;
 
public interface VehicleService {
    Vehicles registerVehicle(Vehicles vehicle);
    Vehicles updateVehicle(Integer id, Vehicles vehicle);
    Vehicles viewVehicle(Integer id);
}