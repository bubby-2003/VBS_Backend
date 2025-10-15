package com.cts.dto;

import com.cts.entity.Customer.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponseDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Status status;
}
