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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="email",referencedColumnName = "email",unique = true)
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

	public Users(String firstName, String lastName, String address, String phone , String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		Auth newAuth = new Auth(); 
	    newAuth.setEmail(email);
	    this.auth = newAuth;
	}
    
}