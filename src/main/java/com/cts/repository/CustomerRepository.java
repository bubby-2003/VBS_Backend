package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.entity.Customer;
 
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findByAuthEmail(String email);
}