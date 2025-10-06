package com.cts.controller;

import com.cts.dto.UsersDTO;
import com.cts.entity.Auth;
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.mapper.UsersMapper;
import com.cts.service.AuthService;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private VehicleService vehicleService;


    @PostMapping
    public UsersDTO createProfile(@RequestBody Users user) {
        Users savedUser = usersService.createProfile(user);
        return UsersMapper.toDTO(savedUser);
    }

    @PutMapping("/{email}")
    public UsersDTO updateProfile(@PathVariable String email, @RequestBody Users user) {
        Users updatedUser = usersService.updateProfile(email, user);
        return UsersMapper.toDTO(updatedUser);
    }

    @GetMapping("/{email}")
    public UsersDTO viewProfile(@PathVariable String email) {
        Users user = usersService.viewProfile(email);
        return UsersMapper.toDTO(user);
    }
    @PostMapping("/{email}/vehicle")
    public Vehicles registerVehicle(@RequestBody Vehicles vehicle,@PathVariable String email) {
        return vehicleService.registerVehicle(vehicle,email);
    }
 
    @PutMapping("/{email}/vehicle/{id}")
    public Vehicles updateVehicle(@PathVariable String email,@PathVariable Integer id, @RequestBody Vehicles vehicle) {
        return vehicleService.updateVehicle(email,id, vehicle);
    }
 
    @GetMapping("/{email}/vehicle")
    public List<Vehicles> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
    @GetMapping("/{email}/vehicle/{id}")
    public Vehicles viewVehicle(@PathVariable String email,@PathVariable Integer id) {
    	return vehicleService.viewVehicle(email,id);
    }
}
