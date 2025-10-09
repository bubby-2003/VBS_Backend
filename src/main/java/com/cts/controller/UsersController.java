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
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.mapper.UsersMapper;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private VehicleService vehicleService;


    @PostMapping
    public UsersResponseDTO createProfile(@RequestBody UsersRequestDTO userDto) {
        Users savedUser = usersService.createProfile(userDto);
        return UsersMapper.toDTO(savedUser);
    }


    @PutMapping("/{email}")
    public UsersResponseDTO updateProfile(@PathVariable String email, @RequestBody UsersRequestDTO userDto) {
        Users updatedUser = usersService.updateProfile(email, userDto);
        return UsersMapper.toDTO(updatedUser);
    }


    @GetMapping("/{email}")
    public UsersResponseDTO viewProfile(@PathVariable String email) {
        Users user = usersService.viewProfile(email);
        return UsersMapper.toDTO(user);
    }
   
    @PostMapping("/{email}/vehicle")
    public VehicleResponseDTO registerVehicle(@RequestBody VehicleRequestDTO vehicleDto, @PathVariable String email) {
        Vehicles savedVehicle = vehicleService.registerVehicle(vehicleDto, email);
        return VehicleMapper.toDTO(savedVehicle);
    }

    @PutMapping("/{email}/vehicle/{id}")
    public VehicleResponseDTO updateVehicle(@PathVariable String email, @PathVariable Integer id, @RequestBody VehicleRequestDTO vehicleDto) {
        Vehicles updatedVehicle = vehicleService.updateVehicle(email, id, vehicleDto);
        return VehicleMapper.toDTO(updatedVehicle);
    }

    @GetMapping("/{email}/vehicle/{id}")
    public VehicleResponseDTO viewVehicle(@PathVariable String email, @PathVariable Integer id) {
        Vehicles vehicle = vehicleService.viewVehicle(email, id);
        return VehicleMapper.toDTO(vehicle);
    }

    @GetMapping("/{email}/vehicle")
    public List<VehicleResponseDTO> getAllVehicles(@PathVariable String email) {
        List<Vehicles> vehicles = vehicleService.getAllVehicles(email);
        return vehicles.stream()
                       .map(VehicleMapper::toDTO)
                       .toList();
    }


}