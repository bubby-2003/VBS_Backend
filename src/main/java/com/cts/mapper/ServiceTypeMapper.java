package com.cts.mapper;

import com.cts.dto.ServiceTypeRequestDTO;
import com.cts.dto.ServiceTypeResponseDTO;
import com.cts.entity.ServiceCenter;
import com.cts.entity.ServiceType;

public class ServiceTypeMapper {
    public static ServiceTypeResponseDTO toDTO(ServiceType serviceType) {
        if (serviceType == null) {
            return null;
        }
        
        ServiceTypeResponseDTO dto = new ServiceTypeResponseDTO();
        
        dto.setServiceTypeId(serviceType.getServiceTypeId());
        dto.setName(serviceType.getName());
        dto.setDescription(serviceType.getDescription());
        dto.setPrice(serviceType.getPrice());
        dto.setStatus(serviceType.getStatus());
        if (serviceType.getServiceCenter() != null) {
            dto.setCenterId(serviceType.getServiceCenter().getServicecenterId());
        }
        
        return dto;
    }
    public static ServiceType toEntity(ServiceTypeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ServiceType serviceType = new ServiceType();
        
        serviceType.setName(dto.getName());
        serviceType.setDescription(dto.getDescription());
        serviceType.setPrice(dto.getPrice());
        return serviceType;
    }
}