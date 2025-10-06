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

@RestController
@RequestMapping("/api/admin/center")
public class ServiceCenterController {
    
    @Autowired
    private ServiceCenterService service;
    
    @Autowired
    private ServiceTypeService typeService;


    @PostMapping
    public ResponseEntity<ServiceCenter> createServiceCenter(@RequestBody ServiceCenter center) {
        ServiceCenter serviceCenter = service.createServiceCenter(center);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceCenter);
    }

    @GetMapping
    public ResponseEntity<List<ServiceCenter>> getAllCenters() {
        List<ServiceCenter> centers = service.getAllServiceCenters();
        return ResponseEntity.ok(centers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCenterById(@PathVariable Integer id) {
        service.deleteServiceCenterById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCenter> getCenterById(@PathVariable Integer id) {
        ServiceCenter center = service.getServiceCenterById(id);
        return ResponseEntity.ok(center);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCenter> updateCenterById(@RequestBody ServiceCenter center, @PathVariable Integer id) {
        ServiceCenter updated = service.updateServiceCenterById(center, id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/service")
    public ResponseEntity<ServiceType> createServiceType(@RequestBody ServiceType serviceType, @PathVariable Integer id) {
        ServiceType newServiceType = typeService.addServiceType(serviceType, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceType);
    }
    
    @GetMapping("/{id}/service")
    public ResponseEntity<List<ServiceType>> getAllServiceTypes(@PathVariable Integer id) {
        List<ServiceType> serviceTypes = typeService.getAllServiceTypes(id);
        return ResponseEntity.ok(serviceTypes);
    }

    @DeleteMapping("/{id}/service/{typeId}")
    public ResponseEntity<Void> deleteServiceTypeById(@PathVariable Integer typeId, @PathVariable Integer id) {
        typeService.deleteServiceTypeById(typeId, id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{id}/service/{typeId}")
    public ResponseEntity<ServiceType> getServiceTypeId(@PathVariable Integer typeId, @PathVariable Integer id) {
        ServiceType serviceType = typeService.getServiceTypeById(typeId, id);
        return ResponseEntity.ok(serviceType);
    }

    @PutMapping("/{id}/service/{typeId}")
    public ResponseEntity<ServiceType> updateServiceTypeById(@RequestBody ServiceType serviceType,
                                                             @PathVariable Integer typeId,
                                                             @PathVariable Integer id) {
        ServiceType updated = typeService.updateServiceType(typeId, id, serviceType);
        return ResponseEntity.ok(updated);
    }
}
