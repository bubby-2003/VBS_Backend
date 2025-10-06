//package com.cts.controller;
// 
//import com.cts.entity.Vehicles;
//import com.cts.service.VehicleService;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.*;
// 
//@RestController
//@RequestMapping("/api/vehicles")
//@RequiredArgsConstructor
//public class VehicleController {
// 
//    private final VehicleService vehicleService;
//
// 
//    @PostMapping("/{email}")
//    public Vehicles registerVehicle(@RequestBody Vehicles vehicle,@PathVariable String email) {
//        return vehicleService.registerVehicle(vehicle,email);
//    }
// 
//    @PutMapping("/{id}")
//    public Vehicles updateVehicle(@PathVariable Integer id, @RequestBody Vehicles vehicle) {
//        return vehicleService.updateVehicle(id, vehicle);
//    }
// 
//    @GetMapping
//    public List<Vehicles> getAllVehicles() {
//        return vehicleService.getAllVehicles();
//    }
//    @GetMapping("/{id}")
//    public Vehicles viewVehicle(@PathVariable Integer id) {
//    	return vehicleService.viewVehicle(id);
//    }
//}
// 