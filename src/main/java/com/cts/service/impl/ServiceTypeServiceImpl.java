package com.cts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.ServiceTypeRequestDTO;
import com.cts.dto.ServiceTypeResponseDTO;
import com.cts.entity.ServiceCenter;
import com.cts.entity.ServiceType;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.ServiceCenterRepository;
import com.cts.repository.ServiceTypeRepository;
import com.cts.service.ServiceTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService {
	private ServiceCenterRepository centerRepository;
	private ServiceTypeRepository typeRepository;
	private ModelMapper mapper;

	@Override
	public ServiceTypeResponseDTO addServiceType(ServiceTypeRequestDTO serviceTypeDto, Integer serviceCenterId) {
	    ServiceCenter center = centerRepository.findById(serviceCenterId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Service Center not found with Id: " + serviceCenterId));
	    
	    ServiceType serviceType = mapper.map(serviceTypeDto, ServiceType.class);
	  
	    serviceType.setServiceCenter(center);
	    serviceType.setStatus(ServiceType.Status.active); 
	    

	    serviceType=typeRepository.save(serviceType);
	    return mapper.map(serviceType, ServiceTypeResponseDTO.class);
	    
	    
	}


	@Override
	public List<ServiceTypeResponseDTO> getAllServiceTypes(Integer serviceCenterId) {
	    centerRepository.findById(serviceCenterId)
	        .orElseThrow(() -> new ResourceNotFoundException("Service Center not found By Id: " + serviceCenterId));

	    List<ServiceType> serviceTypes = typeRepository.findAllByServiceCenterId(serviceCenterId); 
	    return serviceTypes.stream()
	        .map(type -> mapper.map(type, ServiceTypeResponseDTO.class))
	        .collect(Collectors.toList());
	}

	@Override
	public ServiceTypeResponseDTO getServiceTypeById(Integer serviceTypeId,Integer serviceCenterId) {
		
		ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		return mapper.map(serviceType, ServiceTypeResponseDTO.class);
	}

	@Override
	public boolean deleteServiceTypeById(Integer serviceTypeId,Integer serviceCenterId) {
	
		ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		typeRepository.delete(serviceType);
		return true;
		
	}
	@Override
	public ServiceTypeResponseDTO updateServiceType(Integer serviceTypeId, Integer serviceCenterId, ServiceTypeRequestDTO updatedTypeDto) {
	    ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
	        .orElseThrow(() -> new ResourceNotFoundException(
	            "Service Type not found By Id: " + serviceTypeId + " With Service Center Id: " + serviceCenterId));

	    serviceType.setName(updatedTypeDto.getName());
	    serviceType.setDescription(updatedTypeDto.getDescription());
	    serviceType.setPrice(updatedTypeDto.getPrice());

	    serviceType = typeRepository.save(serviceType);
	    return mapper.map(serviceType, ServiceTypeResponseDTO.class);
	}


}











