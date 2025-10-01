package com.cts.mapper;

import com.cts.dto.UsersDTO;
import com.cts.entity.Users;

public class UsersMapper {

    public static UsersDTO toDTO(Users user) {
        UsersDTO dto = new UsersDTO();
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
