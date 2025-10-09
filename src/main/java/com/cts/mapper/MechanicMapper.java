package com.cts.mapper;

import com.cts.dto.MechanicResponseDTO;
import com.cts.entity.Mechanic;

public class MechanicMapper {

    public static MechanicResponseDTO toDTO(Mechanic mechanic) {
        MechanicResponseDTO dto = new MechanicResponseDTO();
        dto.setId(mechanic.getId());
        dto.setEmail(mechanic.getAuth().getEmail());
        dto.setName(mechanic.getName());
        dto.setExpertise(mechanic.getExpertise());
        dto.setSkills(mechanic.getSkills());
        dto.setAvailability(mechanic.getAvailability());
        dto.setRating(mechanic.getRating());
        dto.setIsVerified(mechanic.getIsVerified());
        dto.setStatus(mechanic.getStatus());
        dto.setAddress(mechanic.getAddress());
        dto.setPhone(mechanic.getPhone());
        if (mechanic.getServiceCenter() != null) {
        	dto.setCenterId(mechanic.getServiceCenter().getServicecenterId()); 
        }
        return dto;
    }
}