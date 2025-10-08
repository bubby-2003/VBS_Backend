package com.cts.repository;
 
import com.cts.entity.Vehicles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface VehicleRepository extends JpaRepository<Vehicles, Integer> {
	List<Vehicles> findByUserAuthEmail(String email);
}