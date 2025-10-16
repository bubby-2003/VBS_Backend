package com.cts.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.UsersRequestDTO;
import com.cts.dto.UsersResponseDTO;
import com.cts.dto.VehicleRequestDTO;
import com.cts.dto.VehicleResponseDTO;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing user profiles and their vehicles")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final VehicleService vehicleService;

    @Operation(summary = "Create user profile", description = "Registers a new user profile with details like name, email, and contact info")
    @PostMapping
    public UsersResponseDTO createProfile(@RequestBody @Valid UsersRequestDTO userDto) {
        return usersService.createProfile(userDto);
    }

    @Operation(summary = "Update user profile", description = "Updates an existing user profile using their ID as the identifier")
    @PutMapping("/{id}")
    public UsersResponseDTO updateProfile(@PathVariable int id, @RequestBody @Valid UsersRequestDTO userDto) {
        return usersService.updateProfile(id, userDto);
    }

    @Operation(summary = "View user profile", description = "Fetches details of a user profile by ID")
    @GetMapping("/{id}")
    public UsersResponseDTO viewProfile(@PathVariable int id) {
        return usersService.viewProfile(id);
    }

    @Operation(summary = "Register vehicle", description = "Registers a new vehicle under a user’s profile")
    @PostMapping("/{id}/vehicle")
    public VehicleResponseDTO registerVehicle(@RequestBody @Valid VehicleRequestDTO vehicleDto, @PathVariable int id) {
        return vehicleService.registerVehicle(vehicleDto, id);
    }

    @Operation(summary = "Update vehicle", description = "Updates details of a specific vehicle belonging to a user")
    @PutMapping("/{customerId}/vehicle/{id}")
    public VehicleResponseDTO updateVehicle(@PathVariable int customerId, @PathVariable Integer id,
                                            @RequestBody @Valid VehicleRequestDTO vehicleDto) {
        return vehicleService.updateVehicle(customerId, id, vehicleDto);
    }

    @Operation(summary = "View vehicle by ID", description = "Fetches details of a specific vehicle belonging to a user by vehicle ID")
    @GetMapping("/{customerId}/vehicle/{id}")
    public VehicleResponseDTO viewVehicle(@PathVariable int customerId, @PathVariable Integer id) {
        return vehicleService.viewVehicle(customerId, id);
    }

    @Operation(summary = "View all vehicles by user", description = "Fetches details of all vehicles belonging to a particular user by user ID")
    @GetMapping("/{id}/vehicle")
    public List<VehicleResponseDTO> getAllVehicles(@PathVariable int id) {
        return vehicleService.getAllVehiclesById(id);
    }
}
