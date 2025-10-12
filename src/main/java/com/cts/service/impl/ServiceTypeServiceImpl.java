package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.ServiceTypeRequestDTO;
import com.cts.entity.ServiceCenter;
import com.cts.entity.ServiceType;
import com.cts.exception.ResourceNotFoundException;
import com.cts.mapper.ServiceTypeMapper;
import com.cts.repository.ServiceCenterRepository;
import com.cts.repository.ServiceTypeRepository;
import com.cts.service.ServiceTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService {
	@Autowired
	private ServiceCenterRepository centerRepository;
	@Autowired
	private ServiceTypeRepository typeRepository;

	@Override
	public ServiceType addServiceType(ServiceTypeRequestDTO serviceTypeDto, Integer serviceCenterId) {
	    ServiceCenter center = centerRepository.findById(serviceCenterId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Service Center not found with Id: " + serviceCenterId));

	    ServiceType serviceType = ServiceTypeMapper.toEntity(serviceTypeDto);
	  
	    serviceType.setServiceCenter(center);

	    if (serviceType.getStatus() == null) {
	        serviceType.setStatus(ServiceType.Status.active); 
	    }

	    return typeRepository.save(serviceType);
	}


	@Override
	public List<ServiceType> getAllServiceTypes(Integer serviceCenterId) {
		centerRepository.findById(serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Center not found By Id: "+serviceCenterId));

		List<ServiceType> serviceTypes = typeRepository.findAllByServiceCenterId(serviceCenterId); 
		return serviceTypes;
	}

	@Override
	public ServiceType getServiceTypeById(Integer serviceTypeId,Integer serviceCenterId) {
		
		ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		return serviceType;
	}

	@Override
	public boolean deleteServiceTypeById(Integer serviceTypeId,Integer serviceCenterId) {
	
		ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		typeRepository.delete(serviceType);
		return true;
		
	}
	@Override
	public ServiceType updateServiceType( Integer serviceTypeId,Integer serviceCenterId, ServiceTypeRequestDTO updatedTypeDto) {

		ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		
		if (updatedTypeDto.getName() != null) {
	        serviceType.setName(updatedTypeDto.getName());
	    }
	    if (updatedTypeDto.getDescription() != null) {
	        serviceType.setDescription(updatedTypeDto.getDescription());
	    }
	    if (updatedTypeDto.getPrice() != null) {
	        serviceType.setPrice(updatedTypeDto.getPrice());
	    }
	    return typeRepository.save(serviceType);
	}

}