package com.cts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.ServiceCenter;
import com.cts.entity.ServiceType;
import com.cts.exception.ResourceNotFoundException;
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
	public ServiceType addServiceType(ServiceType serviceType, Integer serviceCenterId) {
	    // Fetch the parent ServiceCenter
	    ServiceCenter center = centerRepository.findById(serviceCenterId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Service Center not found with Id: " + serviceCenterId));

	    // Attach the parent to the child
	    serviceType.setServiceCenter(center);

	    // Optionally normalize status if null
	    if (serviceType.getStatus() == null) {
	        serviceType.setStatus(ServiceType.Status.active); // default
	    }

	    // Save and return
	    return typeRepository.save(serviceType);
	}



	@Override
	public List<ServiceType> getAllServiceTypes(Integer serviceCenterId) {
		centerRepository.findById(serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Center not found By Id: "+serviceCenterId));
		List<ServiceType> serviceTypes=typeRepository.findAllByServiceCenterId(serviceCenterId);
		return serviceTypes;
	}

	@Override
	public ServiceType getServiceTypeById(Integer serviceTypeId,Integer serviceCenterId) {
		// TODO Auto-generated method stub
		ServiceType serviceType=typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		return serviceType;
	}

	@Override
	public boolean deleteServiceTypeById(Integer serviceTypeId,Integer serviceCenterId) {
		// TODO Auto-generated method stub
		ServiceType serviceType = typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		typeRepository.delete(serviceType);
		return true;
		
	}

	@Override
	public ServiceType updateServiceType( Integer serviceTypeId,Integer serviceCenterId, ServiceType updatedType) {
		// TODO Auto-generated method stub
		ServiceType serviceType=typeRepository.findByServiceTypeIdAndServiceCenterId(serviceTypeId, serviceCenterId)
				.orElseThrow(()->new ResourceNotFoundException("Service Type not found By Id: "+serviceTypeId+" With Service Center Id: "+serviceCenterId));
		if (updatedType.getName() != null) {
	        serviceType.setName(updatedType.getName());
	    }
	    if (updatedType.getDescription() != null) {
	        serviceType.setDescription(updatedType.getDescription());
	    }
	    if (updatedType.getPrice() != null) {
	        serviceType.setPrice(updatedType.getPrice());
	    }
	    if (updatedType.getStatus() != null) {
	        serviceType.setStatus(updatedType.getStatus());
	    }
	    return typeRepository.save(serviceType);
	}

}
