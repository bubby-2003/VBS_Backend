package com.cts.service.impl;

import com.cts.entity.Auth;
import com.cts.entity.Booking;
import com.cts.entity.Mechanic;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.repository.BookingRepository;
import com.cts.repository.MechanicRepository;
import com.cts.service.MechanicService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MechanicServiceImpl implements MechanicService{
	private final AuthRepository authrepo;
	private final MechanicRepository mechrepo;
	private final BookingRepository bookrepo;
	
	@Override
	public Mechanic createMechanic(Mechanic mechanic) {
    	Auth authexisiting = authrepo.findById(mechanic.getAuth().getEmail())
    			.orElseThrow(()->new ResourceNotFoundException("email not found"));
    	mechanic.setAuth(authexisiting);
        return mechrepo.save(mechanic);
    }
	@Override
	public Mechanic updateMechanic(String email, Mechanic updatedMechanic) {
        Mechanic existing = mechrepo.findByAuthEmail(email);
        if (existing == null) throw new ResourceNotFoundException("Mechanic not found");

       existing.setName(updatedMechanic.getName());
       existing.setExpertise(updatedMechanic.getExpertise());
       existing.setSkills(updatedMechanic.getSkills());
       existing.setPhone(updatedMechanic.getPhone());
       existing.setAddress(updatedMechanic.getAddress());
       return mechrepo.save(existing);
   }
	@Override
	public Mechanic getMechanicByEmail(String email) {
        return mechrepo.findByAuthEmail(email);
    }
	@Override
	public List<Booking> getAppointments(String email, Integer bookingId) {
        Mechanic mechanic = mechrepo.findByAuthEmail(email);
        if (mechanic == null) throw new ResourceNotFoundException("Mechanic not found");

        return bookrepo.findByMechanicAndBookingId(mechanic, bookingId);
    }
	@Override
	public Booking updateAppointmentStatus(Integer bookingId, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> getFeedback(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}
