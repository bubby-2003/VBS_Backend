package com.cts.repository;
import com.cts.entity.Vehicles;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Customer;
 
public interface VehicleRepository extends JpaRepository<Vehicles, Integer> {

	List<Vehicles> findByCustomer(Customer customer);
//	List<Vehicles> findByCustomerUserAuthEmail(String email);
	List<Vehicles> findByCustomerId(int custmerId);
}