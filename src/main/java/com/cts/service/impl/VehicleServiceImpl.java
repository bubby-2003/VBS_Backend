package com.cts.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.dto.VehicleRequestDTO;
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.UsersRepository;
import com.cts.repository.VehicleRepository;
import com.cts.service.VehicleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UsersRepository userRepository;

    @Override
    public Vehicles registerVehicle(VehicleRequestDTO vehicleDto, String email) {
        Users user = userRepository.findByAuthEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User does not exist: " + email);
        }

        Vehicles vehicle = new Vehicles();
        vehicle.setUser(user);
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
    public Vehicles updateVehicle(String email, Integer id, VehicleRequestDTO vehicleDto) {
        Vehicles existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        Users user = userRepository.findByAuthEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User does not exist: " + email);
        }

        existingVehicle.setUser(user);
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
    public Vehicles viewVehicle(String email, Integer id) {
        Users user = userRepository.findByAuthEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User does not exist: " + email);
        }

        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

	@Override
	public List<Vehicles> getAllVehiclesByEmail(String email) {
//		Users user=userRepository.findByAuthEmail(email);

        List<Vehicles> vehicles = vehicleRepository.findByUserAuthEmail(email);
        if (vehicles == null || vehicles.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found for user: " + email);
        }

        return vehicles;
    }
}
