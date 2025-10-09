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
    private Long contact;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    private String feedback;


    public enum Rating {
        Excellent, Good, Average, Poor
    }
    

    public ServiceCenter(Integer servicecenterId) {
           this.servicecenterId = servicecenterId;
       }


	
}