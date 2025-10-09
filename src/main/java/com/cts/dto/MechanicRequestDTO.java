package com.cts.dto;

import com.cts.entity.Mechanic.Availability;
import com.cts.entity.Mechanic.Rating;
import com.cts.entity.Mechanic.VerificationStatus;
import com.cts.entity.Mechanic.Status;
import lombok.Data;

@Data
public class MechanicRequestDTO {
    private String email;
    private Integer centerId;
    private String name;
    private String expertise;
    private String skills;
    private Availability availability;
    private Rating rating;
    private VerificationStatus isVerified;
    private Status status;
    private String address;
    private String phone;
}