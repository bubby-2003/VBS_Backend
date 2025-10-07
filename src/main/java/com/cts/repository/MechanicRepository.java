package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Mechanic;

public interface MechanicRepository extends JpaRepository<Mechanic, Integer>{
	Mechanic findByAuthEmail(String email);
}
