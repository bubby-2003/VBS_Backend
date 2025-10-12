package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.ServiceCenterRequestDTO; // Import the Request DTO
import com.cts.entity.ServiceCenter;
import com.cts.exception.ResourceNotFoundException;
import com.cts.mapper.ServiceCenterMapper; // Import the Mapper
import com.cts.repository.ServiceCenterRepository;
import com.cts.service.ServiceCenterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCenterServiceImpl implements ServiceCenterService {
    private final ServiceCenterRepository centerRepository;
    @Override
    public ServiceCenter createServiceCenter(ServiceCenterRequestDTO centerDto) {
        ServiceCenter center = ServiceCenterMapper.toEntity(centerDto);
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
        ServiceCenter existingCenter = getServiceCenterById(id); 
        centerRepository.delete(existingCenter);
    }
    @Override
    public ServiceCenter updateServiceCenterById(ServiceCenterRequestDTO centerDto, Integer id) {
        ServiceCenter existingCenter = centerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));

        
        if (centerDto.getContact() != null) {
            existingCenter.setContact(centerDto.getContact());
        }
        if (centerDto.getLocation() != null) {
            existingCenter.setLocation(centerDto.getLocation());
        }
        if (centerDto.getName() != null) {
            existingCenter.setName(centerDto.getName());
        }
        
        return centerRepository.save(existingCenter);
    }
}