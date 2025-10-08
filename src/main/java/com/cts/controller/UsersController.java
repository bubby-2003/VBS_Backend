package com.cts.controller;

import com.cts.dto.UsersDTO;
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.mapper.UsersMapper;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public UsersDTO createProfile(@RequestBody Users user) {
        Users savedUser = usersService.createProfile(user);
        return UsersMapper.toDTO(savedUser);
    }

    @Operation(summary = "Update user profile", description = "Updates an existing user profile using their email as the identifier")
    @PutMapping("/{email}")
    public UsersDTO updateProfile(@PathVariable String email, @RequestBody Users user) {
        Users updatedUser = usersService.updateProfile(email, user);
        return UsersMapper.toDTO(updatedUser);
    }

    @Operation(summary = "View user profile", description = "Fetches details of a user profile by email")
    @GetMapping("/{email}")
    public UsersDTO viewProfile(@PathVariable String email) {
        Users user = usersService.viewProfile(email);
        return UsersMapper.toDTO(user);
    }

    @Operation(summary = "Register vehicle", description = "Registers a new vehicle under a user’s profile")
    @PostMapping("/{email}/vehicle")
    public Vehicles registerVehicle(@RequestBody Vehicles vehicle, @PathVariable String email) {
        return vehicleService.registerVehicle(vehicle, email);
    }

    @Operation(summary = "Update vehicle", description = "Updates details of a specific vehicle belonging to a user")
    @PutMapping("/{email}/vehicle/{id}")
    public Vehicles updateVehicle(@PathVariable String email, @PathVariable Integer id, @RequestBody Vehicles vehicle) {
        return vehicleService.updateVehicle(email, id, vehicle);
    }

    @Operation(summary = "Get all vehicles", description = "Fetches all vehicles registered under all users (note: not filtered by email here)")
    @GetMapping("/{email}/vehicle")
    public List<Vehicles> getAllVehicles(@PathVariable String email) {
        return vehicleService.getAllVehiclesByEmail(email);
    }

    @Operation(summary = "View vehicle by ID", description = "Fetches details of a specific vehicle belonging to a user by vehicle ID")
    @GetMapping("/{email}/vehicle/{id}")
    public Vehicles viewVehicle(@PathVariable String email, @PathVariable Integer id) {
        return vehicleService.viewVehicle(email, id);
    }
}
