package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.entity.ServiceCenter;
import com.cts.entity.ServiceType;
import com.cts.service.ServiceCenterService;
import com.cts.service.ServiceTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    public ResponseEntity<ServiceCenter> createServiceCenter(@RequestBody ServiceCenter center) {
        ServiceCenter serviceCenter = service.createServiceCenter(center);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceCenter);
    }

    @Operation(summary = "Get all service centers", description = "Fetches a list of all registered service centers")
    @GetMapping
    public ResponseEntity<List<ServiceCenter>> getAllCenters() {
        List<ServiceCenter> centers = service.getAllServiceCenters();
        return ResponseEntity.ok(centers);
    }

    @Operation(summary = "Delete service center", description = "Deletes a service center by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCenterById(@PathVariable Integer id) {
        service.deleteServiceCenterById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(summary = "Get service center by ID", description = "Fetches details of a service center by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenter> getCenterById(@PathVariable Integer id) {
        ServiceCenter center = service.getServiceCenterById(id);
        return ResponseEntity.ok(center);
    }

    @Operation(summary = "Update service center", description = "Updates details of a service center by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceCenter> updateCenterById(@RequestBody ServiceCenter center, @PathVariable Integer id) {
        ServiceCenter updated = service.updateServiceCenterById(center, id);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Add service type", description = "Adds a new service type (e.g., oil change, tire replacement) to a service center")
    @PostMapping("/{id}/service")
    public ResponseEntity<ServiceType> createServiceType(@RequestBody ServiceType serviceType, @PathVariable Integer id) {
        ServiceType newServiceType = typeService.addServiceType(serviceType, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceType);
    }
    
    @Operation(summary = "Get all service types", description = "Fetches all service types offered by a specific service center")
    @GetMapping("/{id}/service")
    public ResponseEntity<List<ServiceType>> getAllServiceTypes(@PathVariable Integer id) {
        List<ServiceType> serviceTypes = typeService.getAllServiceTypes(id);
        return ResponseEntity.ok(serviceTypes);
    }

    @Operation(summary = "Delete service type", description = "Deletes a specific service type from a service center")
    @DeleteMapping("/{id}/service/{typeId}")
    public ResponseEntity<Void> deleteServiceTypeById(@PathVariable Integer typeId, @PathVariable Integer id) {
        typeService.deleteServiceTypeById(typeId, id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(summary = "Get service type by ID", description = "Fetches details of a specific service type from a service center")
    @GetMapping("/{id}/service/{typeId}")
    public ResponseEntity<ServiceType> getServiceTypeId(@PathVariable Integer typeId, @PathVariable Integer id) {
        ServiceType serviceType = typeService.getServiceTypeById(typeId, id);
        return ResponseEntity.ok(serviceType);
    }

    @Operation(summary = "Update service type", description = "Updates details of a specific service type in a service center")
    @PutMapping("/{id}/service/{typeId}")
    public ResponseEntity<ServiceType> updateServiceTypeById(@RequestBody ServiceType serviceType,
                                                             @PathVariable Integer typeId,
                                                             @PathVariable Integer id) {
        ServiceType updated = typeService.updateServiceType(typeId, id, serviceType);
        return ResponseEntity.ok(updated);
    }
}
