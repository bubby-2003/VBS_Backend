package com.cts.service;
 
import java.util.List;

import com.cts.entity.Vehicles;
 
public interface VehicleService {
    Vehicles registerVehicle(Vehicles vehicle,String email);
    Vehicles updateVehicle(String email,Integer id, Vehicles vehicle);
    Vehicles viewVehicle(String email,Integer id);
    List<Vehicles> getAllVehiclesByEmail(String email);
}