package com.cts.mapper;

import com.cts.dto.ServiceCenterRequestDTO;
import com.cts.dto.ServiceCenterResponseDTO;
import com.cts.entity.ServiceCenter;

public class ServiceCenterMapper {
    public static ServiceCenterResponseDTO toDTO(ServiceCenter center) {
        if (center == null) {
            return null;
        }
        
        ServiceCenterResponseDTO dto = new ServiceCenterResponseDTO();
        dto.setServicecenterId(center.getServicecenterId());
        dto.setName(center.getName());
        dto.setLocation(center.getLocation());
        dto.setContact(center.getContact());
        dto.setRating(center.getRating());
        dto.setFeedback(center.getFeedback());
        
        return dto;
    }
    public static ServiceCenter toEntity(ServiceCenterRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ServiceCenter center = new ServiceCenter();
        
        center.setName(dto.getName());
        center.setLocation(dto.getLocation());
        center.setContact(dto.getContact());
        return center;
    }
}