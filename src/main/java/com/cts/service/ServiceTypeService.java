package com.cts.service;

import java.util.List;

import com.cts.dto.ServiceTypeRequestDTO;
import com.cts.dto.ServiceTypeResponseDTO;

public interface ServiceTypeService {
	ServiceTypeResponseDTO addServiceType(ServiceTypeRequestDTO serviceTypeDto, Integer serviceCenterId);
    List<ServiceTypeResponseDTO> getAllServiceTypes(Integer serviceCenterId);
    ServiceTypeResponseDTO getServiceTypeById(Integer serviceTypeId, Integer serviceCenterId);
    boolean deleteServiceTypeById(Integer serviceTypeId, Integer serviceCenterId);
    ServiceTypeResponseDTO updateServiceType(Integer serviceTypeId, Integer serviceCenterId, ServiceTypeRequestDTO updatedTypeDto);

}