package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="auth_id",referencedColumnName = "id",unique = true)
    private Auth auth;  
    
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status = Status.active;

    public enum Status {
        active, inActive
    }


}