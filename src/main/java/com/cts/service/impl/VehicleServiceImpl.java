package com.cts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.dto.VehicleRequestDTO;
import com.cts.dto.VehicleResponseDTO;
import com.cts.entity.Customer;
import com.cts.entity.Vehicles;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.CustomerRepository;
import com.cts.repository.VehicleRepository;
import com.cts.service.VehicleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public VehicleResponseDTO registerVehicle(VehicleRequestDTO vehicleDto, int customerId) {
        Customer user = userRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + customerId));

        Vehicles vehicle = modelMapper.map(vehicleDto, Vehicles.class);
        vehicle.setCustomer(user);

        Vehicles savedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(savedVehicle, VehicleResponseDTO.class);
    }

    @Override
    public VehicleResponseDTO updateVehicle(int customerId, Integer vehicleId, VehicleRequestDTO vehicleDto) {
        Vehicles existingVehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + vehicleId));

        Customer user = userRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + customerId));

        modelMapper.map(vehicleDto, existingVehicle);
        existingVehicle.setCustomer(user);

        Vehicles updatedVehicle = vehicleRepository.save(existingVehicle);
        return modelMapper.map(updatedVehicle, VehicleResponseDTO.class);
    }

    @Override
    public VehicleResponseDTO viewVehicle(int customerId, Integer vehicleId) {
        userRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + customerId));

        Vehicles vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + vehicleId));

        return modelMapper.map(vehicle, VehicleResponseDTO.class);
    }

    @Override
    public List<VehicleResponseDTO> getAllVehiclesById(int customerId) {
        List<Vehicles> vehicles = vehicleRepository.findByCustomerId(customerId);
        if (vehicles == null || vehicles.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found for user: " + customerId);
        }

        return vehicles.stream()
            .map(vehicle -> modelMapper.map(vehicle, VehicleResponseDTO.class))
            .collect(Collectors.toList());
    }
}
