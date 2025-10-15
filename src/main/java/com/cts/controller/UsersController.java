package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cts.dto.VehicleRequestDTO;
import com.cts.dto.VehicleResponseDTO;
import com.cts.mapper.VehicleMapper;

import com.cts.dto.UsersResponseDTO;
import com.cts.dto.UsersRequestDTO;

import com.cts.entity.Customer;
import com.cts.entity.Vehicles;
import com.cts.mapper.UsersMapper;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing user profiles and their vehicles")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private VehicleService vehicleService;

    @Operation(summary = "Create user profile", description = "Registers a new user profile with details like name, email, and contact info")
    @PostMapping
    public UsersResponseDTO createProfile(@RequestBody UsersRequestDTO userDto) {
        Customer savedUser = usersService.createProfile(userDto);
        return UsersMapper.toDTO(savedUser);
    }

    @Operation(summary = "Update user profile", description = "Updates an existing user profile using their email as the identifier")
    @PutMapping("/{email}")
    public UsersResponseDTO updateProfile(@PathVariable String email, @RequestBody UsersRequestDTO userDto) {
        Customer updatedUser = usersService.updateProfile(email, userDto);
        return UsersMapper.toDTO(updatedUser);
    }

    @Operation(summary = "View user profile", description = "Fetches details of a user profile by email")
    @GetMapping("/{email}")
    public UsersResponseDTO viewProfile(@PathVariable String email) {
        Customer user = usersService.viewProfile(email);
        return UsersMapper.toDTO(user);
    }
    @Operation(summary = "Register vehicle", description = "Registers a new vehicle under a user’s profile")
    @PostMapping("/{email}/vehicle")
    public VehicleResponseDTO registerVehicle(@RequestBody VehicleRequestDTO vehicleDto, @PathVariable String email) {
        Vehicles savedVehicle = vehicleService.registerVehicle(vehicleDto, email);
        return VehicleMapper.toDTO(savedVehicle);
    }
    @Operation(summary = "Update vehicle", description = "Updates details of a specific vehicle belonging to a user")
    @PutMapping("/{email}/vehicle/{id}")
    public VehicleResponseDTO updateVehicle(@PathVariable String email, @PathVariable Integer id, @RequestBody VehicleRequestDTO vehicleDto) {
        Vehicles updatedVehicle = vehicleService.updateVehicle(email, id, vehicleDto);
        return VehicleMapper.toDTO(updatedVehicle);
    }

    @Operation(summary = "View vehicle by ID", description = "Fetches details of a specific vehicle belonging to a user by vehicle ID")
    @GetMapping("/{email}/vehicle/{id}")
    public VehicleResponseDTO viewVehicle(@PathVariable String email, @PathVariable Integer id) {
        Vehicles vehicle = vehicleService.viewVehicle(email, id);
        return VehicleMapper.toDTO(vehicle);

    }

    @GetMapping("/{email}/vehicle")
    @Operation(summary = "View all vehicles by user specific ", description = "Fetches details of  allvehicle belonging to a particular user by  user email")
    public List<VehicleResponseDTO> getAllVehicles(@PathVariable String email) {
        List<Vehicles> vehicles = vehicleService.getAllVehiclesByEmail(email);
        return vehicles.stream()
                       .map(VehicleMapper::toDTO)
                       .toList();
    }


}