package com.cts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_email",referencedColumnName = "email")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicles vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mechanic_email",referencedColumnName = "email",nullable = true)
    private Mechanic mechanic;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private ServiceCenter serviceCenter;

    private LocalDateTime date;
    private String timeslot;
    private String feedback;
    //private String status = "Pending";

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private VerificationStatus isVerified = VerificationStatus.No;

    public enum VerificationStatus {
        Yes, No, Rejected
    }
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.Upcoming;

    public enum Status {
        Upcoming, Completed, Canceled
    }
}