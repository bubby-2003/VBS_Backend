package com.cts.service;

import java.util.List;

import com.cts.dto.ServiceTypeRequestDTO;
import com.cts.entity.ServiceType;

public interface ServiceTypeService {
    ServiceType addServiceType(ServiceTypeRequestDTO serviceTypeDto, Integer serviceCenterId);
    List<ServiceType> getAllServiceTypes(Integer serviceCenterId);
    ServiceType getServiceTypeById(Integer serviceTypeId, Integer serviceCenterId);
    boolean deleteServiceTypeById(Integer serviceTypeId, Integer serviceCenterId);
    ServiceType updateServiceType(Integer serviceTypeId, Integer serviceCenterId, ServiceTypeRequestDTO updatedTypeDto);

}