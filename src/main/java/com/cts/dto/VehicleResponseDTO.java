package com.cts.dto;
import com.cts.entity.Users.Status;
import lombok.Data;

@Data
public class VehicleDTO {
	private Integer id;
    private String email;
    private String year;
    private String lastName;
    private String address;
    private String phone;
    private Status status;

}
