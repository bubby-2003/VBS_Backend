package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    @Id
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
}