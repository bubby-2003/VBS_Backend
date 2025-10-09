package com.cts.dto;

import com.cts.entity.Users.Status;
import lombok.Data;

@Data
public class UsersDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Status status;
}
