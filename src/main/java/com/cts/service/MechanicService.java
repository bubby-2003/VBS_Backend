package com.cts.service;

import com.cts.entity.Mechanic;
import com.cts.entity.Booking;

import java.util.List;

public interface MechanicService {
	    Mechanic createMechanic(Mechanic mechanic);
	    Mechanic updateMechanic(String email ,Mechanic mechanic);
	    Mechanic getMechanicByEmail(String email); 
	    List<Booking> getAppointments(String email,Integer id );
	    Booking updateAppointmentStatus(Integer bookingId, String status);
	    List<String> getFeedback(String email); 
    }