package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.entity.Customer;
 
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findByAuthEmail(String email);

	@Query("SELECT c FROM Customer c WHERE c.auth.id = :authId")
	Optional<Customer> findByAuthId(@Param("authId") Integer authId);

	
}