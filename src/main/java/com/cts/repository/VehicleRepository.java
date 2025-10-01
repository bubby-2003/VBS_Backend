package com.cts.repository;
 
import com.cts.entity.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface VehicleRepository extends JpaRepository<Vehicles, Integer> {
}