package com.cts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.MechanicRequestDTO;
import com.cts.entity.Auth;
import com.cts.entity.Mechanic;
import com.cts.entity.ServiceCenter;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.repository.BookingRepository;
import com.cts.repository.MechanicRepository;
import com.cts.service.MechanicService;

import lombok.AllArgsConstructor; 

@Service
@AllArgsConstructor
public class MechanicServiceImpl implements MechanicService {

    private final AuthRepository authrepo;
    private final MechanicRepository mechrepo;
    private final BookingRepository bookrepo;

    @Override
    public Mechanic createMechanic(MechanicRequestDTO mechanicDto) {
        Auth auth = authrepo.findByEmail(mechanicDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        Mechanic mechanic = new Mechanic();
        mechanic.setAuth(auth);
        mechanic.setName(mechanicDto.getName());
        mechanic.setExpertise(mechanicDto.getExpertise());
        mechanic.setSkills(mechanicDto.getSkills());
        mechanic.setAvailability(mechanicDto.getAvailability());
        mechanic.setRating(mechanicDto.getRating());
        mechanic.setIsVerified(mechanicDto.getIsVerified());
        mechanic.setStatus(mechanicDto.getStatus());
        mechanic.setAddress(mechanicDto.getAddress());
        mechanic.setPhone(mechanicDto.getPhone());

        if (mechanicDto.getCenterId() != null) {
            mechanic.setServiceCenter(new ServiceCenter(mechanicDto.getCenterId()));
        }

        return mechrepo.save(mechanic);
    }

    @Override
    public Mechanic updateMechanic(int id, MechanicRequestDTO mechanicDto) {
        Mechanic existing = mechrepo.findById(id)
        		.orElseThrow(()->new ResourceNotFoundException("Mechanic not found"));

        existing.setName(mechanicDto.getName());
        existing.setExpertise(mechanicDto.getExpertise());
        existing.setSkills(mechanicDto.getSkills());
        existing.setAvailability(mechanicDto.getAvailability());
        existing.setRating(mechanicDto.getRating());
        existing.setIsVerified(mechanicDto.getIsVerified());
        existing.setStatus(mechanicDto.getStatus());
        existing.setAddress(mechanicDto.getAddress()); 
        existing.setPhone(mechanicDto.getPhone());

        if (mechanicDto.getCenterId() != null) {
            existing.setServiceCenter(new ServiceCenter(mechanicDto.getCenterId()));
        }

        return mechrepo.save(existing);
    }

    @Override
    public Mechanic getMechanicById(int id) {
    	Mechanic mechanic = mechrepo.findById(id)
        		.orElseThrow(()->new ResourceNotFoundException("Mechanic not found"));
        return mechanic;
    }

    
}