package com.cts.dto;

import com.cts.entity.Users.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersRequestDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Status status;
}
