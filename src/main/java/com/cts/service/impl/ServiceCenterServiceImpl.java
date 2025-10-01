
package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.ServiceCenter;
import com.cts.repository.ServiceCenterRepository;
import com.cts.service.ServiceCenterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCenterServiceImpl implements ServiceCenterService {
	@Autowired
	private ServiceCenterRepository centerRepository;
	
	public ServiceCenter createServiceCenter(ServiceCenter center) {
		
		return centerRepository.save(center);
	}
	
	public List<ServiceCenter> getAllServiceCenters(){
		return centerRepository.findAll();
	}
	
	public ServiceCenter getServiceCenterById(Integer id) {
		return centerRepository.findById(id).orElse(null);
	}
	
	public void deleteServiceCenterById(Integer id) {
		centerRepository.deleteById(id);
	}
	
	public ServiceCenter updateServiceCenterById(ServiceCenter center,Integer id) {
		ServiceCenter existingCenter=centerRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Service Center Not found with id "+id));
		
		existingCenter.setContact(center.getContact());
		existingCenter.setFeedback(center.getFeedback());
		existingCenter.setLocation(center.getLocation());
		existingCenter.setName(center.getName());
		existingCenter.setRating(center.getRating());
		
		return centerRepository.save(existingCenter);
		
	}

}
