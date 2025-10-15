package com.cts.mapper;

import com.cts.dto.UsersResponseDTO;
import com.cts.entity.Customer;

public class UsersMapper {

    public static UsersResponseDTO toDTO(Customer user) {
        UsersResponseDTO dto = new UsersResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getAuth().getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus());
        return dto;
    }
}
