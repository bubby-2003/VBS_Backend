package com.cts.service;

import com.cts.dto.MechanicRequestDTO;
import com.cts.entity.Mechanic;

import java.util.List;

public interface MechanicService {


	Mechanic createMechanic(MechanicRequestDTO mechanicDto);
	Mechanic updateMechanic(int id, MechanicRequestDTO mechanicDto);
//	Mechanic updateMechanic(String email, MechanicRequestDTO mechanicDto);
	Mechanic getMechanicById(int id);
//	Mechanic getMechanicByEmail(String email);
    }

