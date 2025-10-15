package com.cts.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.dto.ServiceCenterRequestDTO;
import com.cts.dto.ServiceCenterResponseDTO;
import com.cts.dto.ServiceTypeRequestDTO;
import com.cts.dto.ServiceTypeResponseDTO;

import com.cts.service.ServiceCenterService;
import com.cts.service.ServiceTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/center")
@Tag(name = "Service Center Management", description = "APIs for managing service centers and their service types")

public class ServiceCenterController {
    
    @Autowired
    private ServiceCenterService service;
    
    @Autowired
    private ServiceTypeService typeService;


    @Operation(summary = "Create a new service center", description = "Registers a new service center with details like name, location, and contact info")
    @PostMapping
    public ResponseEntity<ServiceCenterResponseDTO> createServiceCenter(@RequestBody @Valid ServiceCenterRequestDTO centerDto) {
    	ServiceCenterResponseDTO serviceCenterResponse = service.createServiceCenter(centerDto);
        return new ResponseEntity<ServiceCenterResponseDTO>(serviceCenterResponse,HttpStatus.CREATED);
    }

    @Operation(summary = "Get all service centers", description = "Fetches a list of all registered service centers")
    @GetMapping
    public ResponseEntity<List<ServiceCenterResponseDTO>> getAllCenters() {
        List<ServiceCenterResponseDTO> centerDtos = service.getAllServiceCenters();
        return new ResponseEntity<List<ServiceCenterResponseDTO>>(centerDtos,HttpStatus.OK);
    }

    @Operation(summary = "Delete service center", description = "Deletes a service center by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCenterById(@PathVariable Integer id) {
        service.deleteServiceCenterById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get service center by ID", description = "Fetches details of a service center by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenterResponseDTO> getCenterById(@PathVariable Integer id) {
    	ServiceCenterResponseDTO center = service.getServiceCenterById(id);
        return new ResponseEntity<ServiceCenterResponseDTO>(center,HttpStatus.OK);
    }

    @Operation(summary = "Update service center", description = "Updates details of a service center by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceCenterResponseDTO> updateCenterById(@RequestBody @Valid ServiceCenterRequestDTO centerDto, @PathVariable Integer id) {
    	ServiceCenterResponseDTO updated = service.updateServiceCenterById(centerDto, id); 
    	return new ResponseEntity<ServiceCenterResponseDTO>(updated,HttpStatus.OK);
    }


    @Operation(summary = "Add service type", description = "Adds a new service type (e.g., oil change, tire replacement) to a service center")
    @PostMapping("/{id}/service")
    public ResponseEntity<ServiceTypeResponseDTO> createServiceType(@RequestBody @Valid ServiceTypeRequestDTO serviceTypeDto, @PathVariable Integer id) {
    	ServiceTypeResponseDTO newServiceType = typeService.addServiceType(serviceTypeDto, id);
        return new ResponseEntity<ServiceTypeResponseDTO>(newServiceType,HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get all service types", description = "Fetches all service types offered by a specific service center")
    @GetMapping("/{id}/service")
    public ResponseEntity<List<ServiceTypeResponseDTO>> getAllServiceTypes(@PathVariable Integer id) {
        List<ServiceTypeResponseDTO> serviceTypes = typeService.getAllServiceTypes(id);
        
        return new ResponseEntity<List<ServiceTypeResponseDTO>>(serviceTypes,HttpStatus.OK);
    }

    @Operation(summary = "Delete service type", description = "Deletes a specific service type from a service center")
    @DeleteMapping("/{id}/service/{typeId}")
    public ResponseEntity<Void> deleteServiceTypeById(@PathVariable Integer typeId, @PathVariable Integer id) {
        typeService.deleteServiceTypeById(typeId, id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get service type by ID", description = "Fetches details of a specific service type from a service center")
    @GetMapping("/{id}/service/{typeId}")
    public ResponseEntity<ServiceTypeResponseDTO> getServiceTypeId(@PathVariable Integer typeId, @PathVariable Integer id) {
    	ServiceTypeResponseDTO serviceType = typeService.getServiceTypeById(typeId, id);
        return new ResponseEntity<ServiceTypeResponseDTO>(serviceType,HttpStatus.OK);
    }

    @Operation(summary = "Update service type", description = "Updates details of a specific service type in a service center")
    @PutMapping("/{id}/service/{typeId}")
    public ResponseEntity<ServiceTypeResponseDTO> updateServiceTypeById(@RequestBody ServiceTypeRequestDTO serviceTypeDto,
                                                             @PathVariable Integer typeId,
                                                             @PathVariable Integer id) {
    	ServiceTypeResponseDTO updatedServiceType = typeService.updateServiceType(typeId, id, serviceTypeDto); 
        return new ResponseEntity<ServiceTypeResponseDTO>(updatedServiceType,HttpStatus.OK);
    }
}