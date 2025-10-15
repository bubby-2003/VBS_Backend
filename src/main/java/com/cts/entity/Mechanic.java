package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "mechanic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @OneToOne
    @JoinColumn(name="auth_id", referencedColumnName = "id",unique = true)
    private Auth auth; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id",nullable = true)
    private ServiceCenter serviceCenter;

    private String name;
    private String expertise;
    private String skills;
    
    @Enumerated(EnumType.STRING)
    private Availability availability;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerified = VerificationStatus.no;

    @Enumerated(EnumType.STRING)
    private Status status = Status.active;

    private String address;

    @Column(length = 10)
    private String phone;

  
    public enum Availability { Available, Unavailable, On_Leave }
    public enum Rating { Excellent, Good, Average, Poor }
    public enum VerificationStatus { yes, no, rejected }
    public enum Status { active, inActive }

    
}