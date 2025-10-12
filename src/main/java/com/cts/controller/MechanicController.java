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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/mechanic")
@Tag(name = "Mechanic Management",description = "Create Web Api's for Mechanic Operations")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;

    @Operation(
        summary = "Create a new mechanic",
        description = "Registers a new mechanic with details such as name, email, specialization, and availability"
    )
    
    @PostMapping
    public ResponseEntity<MechanicResponseDTO> createMechanic(@RequestBody MechanicRequestDTO mechanicDto) {
        Mechanic mechanic = mechanicService.createMechanic(mechanicDto);
        return new ResponseEntity<>(MechanicMapper.toDTO(mechanic), HttpStatus.CREATED);
    }
    
    @Operation(
          summary = "Update mechanic details",
          description = "Updates an existing mechanic’s information using their email as the identifier"
      )
    @PutMapping("/{email}")
    public ResponseEntity<MechanicResponseDTO> updateMechanic(@PathVariable String email, @RequestBody MechanicRequestDTO mechanicDto) {
        Mechanic mechanic = mechanicService.updateMechanic(email, mechanicDto);
        return new ResponseEntity<>(MechanicMapper.toDTO(mechanic), HttpStatus.OK);
    }

    @Operation(
        summary = "Get mechanic by email",
        description = "Fetches mechanic details using their email address"
    )
    @GetMapping("/{email}")
    public ResponseEntity<MechanicResponseDTO> getMechanic(@PathVariable @Valid String email) {
        Mechanic mechanic = mechanicService.getMechanicByEmail(email);
        return new ResponseEntity<>(MechanicMapper.toDTO(mechanic), HttpStatus.OK);
    }

     
}