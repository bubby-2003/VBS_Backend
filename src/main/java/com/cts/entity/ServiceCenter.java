package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "service_center")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer servicecenterId;

    private String name;
    private String location;
    private String contact;

    @Enumerated(EnumType.STRING)
    private Rating rating=Rating.GOOD;

    private String feedback;


    public enum Rating {
        EXCELLENT, GOOD, AVERAGE, POOR
    }
    

    public ServiceCenter(Integer servicecenterId) {
           this.servicecenterId = servicecenterId;
       }


	
}