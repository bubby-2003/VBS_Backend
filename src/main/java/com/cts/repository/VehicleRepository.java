package com.cts.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Users;
import com.cts.entity.Vehicles;
 
public interface VehicleRepository extends JpaRepository<Vehicles, Integer> {
	List<Vehicles> findByUser(Users user);
}