package com.cts.service.impl;
 
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.UsersRepository;
import com.cts.repository.VehicleRepository;
import com.cts.service.VehicleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
 
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
 
    private final VehicleRepository vehicleRepository;
    private final UsersRepository userRepository;
 
    @Override
    public Vehicles registerVehicle(Vehicles vehicle,String email) {
    	Users user=userRepository.findByAuthEmail(email);
    	if(user==null) {
    		throw new ResourceNotFoundException("User Doesnot exist: "+vehicle.getUser().getAuth().getEmail());
    	}
    	vehicle.setUser(user);
        return vehicleRepository.save(vehicle);
    }
 
    @Override
    public Vehicles updateVehicle(String email,Integer id, Vehicles updatedVehicle) {
        Vehicles existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        Users existingUser = userRepository.findByAuthEmail(email);
    	if(existingUser==null) {
    		throw new ResourceNotFoundException("User Doesnot exist: "+email);
    	}
        existingVehicle.setUser(existingUser);
        existingVehicle.setMake(updatedVehicle.getMake());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setYear(updatedVehicle.getYear());
        existingVehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setEngine(updatedVehicle.getEngine());
        existingVehicle.setAbs(updatedVehicle.getAbs());
        existingVehicle.setDoors(updatedVehicle.getDoors());
        existingVehicle.setAc(updatedVehicle.getAc());
        existingVehicle.setTransmission(updatedVehicle.getTransmission());
        existingVehicle.setFuel(updatedVehicle.getFuel());

        return vehicleRepository.save(existingVehicle);
    }

 
    @Override
    public Vehicles viewVehicle(String email,Integer id) {
    	Users existingUser = userRepository.findByAuthEmail(email);
    	if(existingUser==null) {
    		throw new ResourceNotFoundException("User Doesnot exist: "+email);
    	}
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }

	@Override
	public List<Vehicles> getAllVehicles() {
	    List<Vehicles> vehicles = vehicleRepository.findAll();
	    if (vehicles == null || vehicles.isEmpty()) {
	        throw new ResourceNotFoundException("No vehicles found. Please add one.");
	    }
	    return vehicles;
	}

}