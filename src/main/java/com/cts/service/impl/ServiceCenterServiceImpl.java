package com.cts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.dto.ServiceCenterRequestDTO;
import com.cts.dto.ServiceCenterResponseDTO;
import com.cts.entity.ServiceCenter;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ServiceCenterRepository;
import com.cts.service.ServiceCenterService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceCenterServiceImpl implements ServiceCenterService {
    private final ServiceCenterRepository centerRepository;
    private final ModelMapper mapper;
    
    
    @Override
    public ServiceCenterResponseDTO createServiceCenter(ServiceCenterRequestDTO centerDto) {
    	
        ServiceCenter center = mapper.map(centerDto, ServiceCenter.class);
        
        center=centerRepository.save(center);
        ServiceCenterResponseDTO serviceResponse=mapper.map(center, ServiceCenterResponseDTO.class);
        return serviceResponse;
    }
    @Override
    public List<ServiceCenterResponseDTO> getAllServiceCenters() {
        List<ServiceCenter> centers = centerRepository.findAll();
        return centers.stream()
                      .map(center -> mapper.map(center, ServiceCenterResponseDTO.class))
                      .collect(Collectors.toList());
    }

    @Override
    public ServiceCenterResponseDTO getServiceCenterById(Integer id) {
        ServiceCenter center= centerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));
        ServiceCenterResponseDTO centerResponse=mapper.map(center, ServiceCenterResponseDTO.class);
        return centerResponse;
    }
    @Override
    public void deleteServiceCenterById(Integer id) {
        centerRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));
        centerRepository.deleteById(id);
    }
    @Override
    public ServiceCenterResponseDTO updateServiceCenterById(ServiceCenterRequestDTO centerDto, Integer id) {
        ServiceCenter center = centerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with Id: " + id));

        // Update fields individually or use a safe mapper
        center.setName(centerDto.getName());
        center.setLocation(centerDto.getLocation());
        center.setContact(centerDto.getContact());


        center = centerRepository.save(center);
        return mapper.map(center, ServiceCenterResponseDTO.class);
    }

}