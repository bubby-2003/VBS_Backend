package com.cts.service;

import java.util.List;

import com.cts.entity.ServiceType;

public interface ServiceTypeService {
	ServiceType addServiceType(ServiceType serviceType, Integer serviceCenterId);
    List<ServiceType> getAllServiceTypes(Integer serviceCenterId);
    ServiceType getServiceTypeById(Integer serviceTypeId,Integer serviceCenterId);
    boolean deleteServiceTypeById(Integer serviceTypeId,Integer serviceCenterId);
    ServiceType updateServiceType(Integer serviceTypeId,Integer serviceCenterId,  ServiceType updatedType);

}
