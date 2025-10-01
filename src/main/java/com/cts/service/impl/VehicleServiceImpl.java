package com.cts.service.impl;
 
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.UsersRepository;
import com.cts.repository.VehicleRepository;
import com.cts.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
 
    private final VehicleRepository vehicleRepository;
    private final UsersRepository userRepository;
 
    @Override
    public Vehicles registerVehicle(Vehicles vehicle) {
    	Users user=userRepository.findByAuthEmail(vehicle.getUser().getAuth().getEmail());
    	if(user==null) {
    		throw new ResourceNotFoundException("User Doesnot exist: "+vehicle.getUser().getAuth().getEmail());
    	}
    	vehicle.setUser(user);
        return vehicleRepository.save(vehicle);
    }
 
    @Override
    public Vehicles updateVehicle(Integer id, Vehicles updatedVehicle) {
        Vehicles existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        // Fetch the managed Users entity from DB using email or ID
        String email = updatedVehicle.getUser().getAuth().getEmail(); // or use user ID if available
        Users existingUser = userRepository.findByAuthEmail(email);
    	if(existingUser==null) {
    		throw new ResourceNotFoundException("User Doesnot exist: "+updatedVehicle.getUser().getAuth().getEmail());
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
    public Vehicles viewVehicle(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }
}