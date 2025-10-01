package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;

   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="email",referencedColumnName = "email")
    private Users user;

    private String make;
    private String model;
    private Integer year;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type",nullable=false)
    private VehicleType vehicleType = VehicleType.car;

    private String engine;
    private String abs;
    private String doors;
    private String ac;
    private String transmission;
    private String fuel;

    public enum VehicleType {
        car, bike
    }
}