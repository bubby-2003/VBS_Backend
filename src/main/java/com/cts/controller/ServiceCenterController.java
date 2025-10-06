package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.ServiceCenter;
import com.cts.service.ServiceCenterService;

@RestController
@RequestMapping("/api/service-center")
public class ServiceCenterController {
	
	@Autowired
	ServiceCenterService service;
	@PostMapping
	public ServiceCenter createServiceCenter(@RequestBody ServiceCenter center) {
		ServiceCenter serviceCenter=service.createServiceCenter(center);
		return serviceCenter;
	}
	@GetMapping
	public List<ServiceCenter> getAllCenters(){
		List<ServiceCenter> centers=service.getAllServiceCenters();
		return centers;
	}
	
	@DeleteMapping("/{id}")
	public void deleteServiceCenterById(@PathVariable Integer id) {
		service.deleteServiceCenterById(id);
	}
	
	@GetMapping("/{id}")
	public ServiceCenter getCenterById(@PathVariable Integer id) {
		return service.getServiceCenterById(id);
	}
	@PutMapping("/{id}")
	public ServiceCenter updateCenterById(@RequestBody ServiceCenter center,@PathVariable Integer id) {
		return service.updateServiceCenterById(center,id);
	}
}
