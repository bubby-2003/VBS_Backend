package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.ServiceCenter;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ServiceCenterRepository;
import com.cts.service.ServiceCenterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCenterServiceImpl implements ServiceCenterService {
	@Autowired
    private final ServiceCenterRepository centerRepository;

    @Override
    public ServiceCenter createServiceCenter(ServiceCenter center) {
        return centerRepository.save(center);
    }

    @Override
    public List<ServiceCenter> getAllServiceCenters() {
        return centerRepository.findAll();
    }

    @Override
    public ServiceCenter getServiceCenterById(Integer id) {
        return centerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));
    }

    @Override
    public void deleteServiceCenterById(Integer id) {
        ServiceCenter existingCenter = centerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));
        centerRepository.delete(existingCenter);
    }

    @Override
    public ServiceCenter updateServiceCenterById(ServiceCenter center, Integer id) {
        ServiceCenter existingCenter = centerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));

        // Update only non-null fields (safe check)
        if (center.getContact() != null) existingCenter.setContact(center.getContact());
        if (center.getFeedback() != null) existingCenter.setFeedback(center.getFeedback());
        if (center.getLocation() != null) existingCenter.setLocation(center.getLocation());
        if (center.getName() != null) existingCenter.setName(center.getName());
        if (center.getRating() != null) existingCenter.setRating(center.getRating());

        return centerRepository.save(existingCenter);
    }
}
