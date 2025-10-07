package com.cts.controller;

import com.cts.entity.Mechanic;
import com.cts.service.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/mechanic")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;

    @PostMapping
    public ResponseEntity<Mechanic> createMechanic(@RequestBody Mechanic mechanic) {
        mechanic = mechanicService.createMechanic(mechanic);
        return new ResponseEntity<Mechanic>(mechanic, HttpStatus.CREATED);
    }
    
    @PutMapping("/{email}")
    public ResponseEntity<Mechanic> updateMechanic(@PathVariable String email, @RequestBody Mechanic updatedMechanic) {
        Mechanic mechanic = mechanicService.updateMechanic(email, updatedMechanic);
        return new ResponseEntity<Mechanic>(mechanic, HttpStatus.OK);
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<Mechanic> getMechanic(@PathVariable String email) {
        Mechanic mechanic = mechanicService.getMechanicByEmail(email);
        return new ResponseEntity<Mechanic>(mechanic, HttpStatus.OK);
    }
    
//    @GetMapping("/{email}/appointments/{id}")
//    public ResponseEntity<List<Booking>> getAppointments(@PathVariable String email, @PathVariable Integer id) {
//        return ResponseEntity.ok(mechanicService.getAppointments(email, id));
//    }
    
//    @PutMapping("/{email}/appointments/{id}")
//    public ResponseEntity<Booking> updateAppointmentStatus(@PathVariable String email, @PathVariable Integer id, @RequestBody Booking.Status status) {
//        Booking updated = mechanicService.updateAppointmentStatus(email, id, status);
//        return ResponseEntity.ok(updated);
//    }
//    
//    @GetMapping("/{email}/feedback/{id}")
//    public ResponseEntity<String> getFeedback(@PathVariable String email, @PathVariable Integer id) {
//        return ResponseEntity.ok(mechanicService.getFeedback(email, id));
//    }
//    
//    @PutMapping("/{email}/availability")
//    public ResponseEntity<Mechanic> updateAvailability(@PathVariable String email, @RequestBody Mechanic.Availability availability) {
//        Mechanic updated = mechanicService.updateAvailability(email, availability);
//        return ResponseEntity.ok(updated);
//    }  
}