package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceTypeId;

    private String description;
    private Double price;


    @Enumerated(EnumType.STRING)
    private Status status;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id",nullable = false)
    private ServiceCenter serviceCenter;

    public enum Status {
        active, inActive
    }
}