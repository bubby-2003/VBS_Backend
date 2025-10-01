package com.cts.controller;
 
import com.cts.entity.Users;
import com.cts.entity.Vehicles;
import com.cts.service.UsersService;
import com.cts.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
 
    private final VehicleService vehicleService;

 
    @PostMapping("/create")
    public Vehicles registerVehicle(@RequestBody Vehicles vehicle) {
        return vehicleService.registerVehicle(vehicle);
    }
 
    @PutMapping("/{id}")
    public Vehicles updateVehicle(@PathVariable Integer id, @RequestBody Vehicles vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }
 
    @GetMapping("/{id}")
    public Vehicles viewVehicle(@PathVariable Integer id) {
        return vehicleService.viewVehicle(id);
    }
}
 