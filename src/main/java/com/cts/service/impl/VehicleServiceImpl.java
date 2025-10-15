package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.VehicleRequestDTO;
import com.cts.entity.Customer;
import com.cts.entity.Vehicles;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.CustomerRepository;
import com.cts.repository.VehicleRepository;
import com.cts.service.VehicleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

	@Autowired
    private final VehicleRepository vehicleRepository;
	@Autowired
    private final CustomerRepository userRepository;

    @Override
    public Vehicles registerVehicle(VehicleRequestDTO vehicleDto, int id) {
    	Customer user=userRepository.findByAuthId(id)
        		.orElseThrow(()->new ResourceNotFoundException("User not found with Id: " + id));

        Vehicles vehicle = new Vehicles();
        vehicle.setCustomer(user);
        vehicle.setMake(vehicleDto.getMake());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYear(vehicleDto.getYear());
        vehicle.setRegistrationNumber(vehicleDto.getRegistrationNumber());
        vehicle.setVehicleType(vehicleDto.getVehicleType());
        vehicle.setEngine(vehicleDto.getEngine());
        vehicle.setAbs(vehicleDto.getAbs());
        vehicle.setDoors(vehicleDto.getDoors());
        vehicle.setAc(vehicleDto.getAc());
        vehicle.setTransmission(vehicleDto.getTransmission());
        vehicle.setFuel(vehicleDto.getFuel());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicles updateVehicle(int customerId, Integer id, VehicleRequestDTO vehicleDto) {
        Vehicles existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        Customer user = userRepository.findByAuthId(customerId)
        		.orElseThrow(()->new ResourceNotFoundException("User not found with Id: " + customerId));
 
        existingVehicle.setCustomer(user);
        existingVehicle.setMake(vehicleDto.getMake());
        existingVehicle.setModel(vehicleDto.getModel());
        existingVehicle.setYear(vehicleDto.getYear());
        existingVehicle.setRegistrationNumber(vehicleDto.getRegistrationNumber());
        existingVehicle.setVehicleType(vehicleDto.getVehicleType());
        existingVehicle.setEngine(vehicleDto.getEngine());
        existingVehicle.setAbs(vehicleDto.getAbs());
        existingVehicle.setDoors(vehicleDto.getDoors());
        existingVehicle.setAc(vehicleDto.getAc());
        existingVehicle.setTransmission(vehicleDto.getTransmission());
        existingVehicle.setFuel(vehicleDto.getFuel());

        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public Vehicles viewVehicle(int customerId, Integer id) {
    	userRepository.findByAuthId(customerId)
        		.orElseThrow(()->new ResourceNotFoundException("User not found with Id: " + customerId));

        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

	@Override
	public List<Vehicles> getAllVehiclesById(int custmerId) {
//		Users user=userRepository.findByAuthEmail(email);

        List<Vehicles> vehicles = vehicleRepository.findByUserAuthId(custmerId);
        if (vehicles == null || vehicles.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found for user: " + custmerId);
        }

        return vehicles;
    }
}
