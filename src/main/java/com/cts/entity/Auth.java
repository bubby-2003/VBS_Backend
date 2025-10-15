package com.cts.entity;

import com.cts.enums.AuthRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id; 
	 
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private AuthRole role;

  

}