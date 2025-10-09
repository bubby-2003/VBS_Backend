package com.cts.controller;

import com.cts.dto.MechanicRequestDTO;
import com.cts.dto.MechanicResponseDTO;
import com.cts.entity.Mechanic;
import com.cts.mapper.MechanicMapper;
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
    public ResponseEntity<MechanicResponseDTO> createMechanic(@RequestBody MechanicRequestDTO mechanicDto) {
        Mechanic mechanic = mechanicService.createMechanic(mechanicDto);
        return new ResponseEntity<>(MechanicMapper.toDTO(mechanic), HttpStatus.CREATED);
    }

    @PutMapping("/{email}")
    public ResponseEntity<MechanicResponseDTO> updateMechanic(@PathVariable String email, @RequestBody MechanicRequestDTO mechanicDto) {
        Mechanic mechanic = mechanicService.updateMechanic(email, mechanicDto);
        return new ResponseEntity<>(MechanicMapper.toDTO(mechanic), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<MechanicResponseDTO> getMechanic(@PathVariable String email) {
        Mechanic mechanic = mechanicService.getMechanicByEmail(email);
        return new ResponseEntity<>(MechanicMapper.toDTO(mechanic), HttpStatus.OK);
    }
}