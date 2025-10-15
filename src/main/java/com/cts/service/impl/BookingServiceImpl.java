package com.cts.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.cts.dto.BookingRequestDTO;
import com.cts.entity.Booking;
import com.cts.entity.Mechanic;
import com.cts.entity.ServiceCenter;
import com.cts.entity.Customer;
import com.cts.entity.Vehicles;
import com.cts.exception.ResourceNotFoundException;
import com.cts.mapper.BookingMapper;
import com.cts.repository.BookingRepository;
import com.cts.repository.MechanicRepository;
import com.cts.repository.ServiceCenterRepository;
import com.cts.repository.CustomerRepository;
import com.cts.repository.VehicleRepository;
import com.cts.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	
	private final BookingRepository brepo;
    private final CustomerRepository userRepo;
    private final VehicleRepository vehicleRepo;
    private final ServiceCenterRepository centerRepo;
    private final MechanicRepository mechanicRepo;
    
    @Override
	public Booking createBooking(BookingRequestDTO bookingDto) {
        Customer user = userRepo.findByAuthEmail(bookingDto.getUserEmail());
        if(user == null) {
        	throw new ResourceNotFoundException("User not found with email: " + bookingDto.getUserEmail());
        }
        
        Vehicles vehicle = vehicleRepo.findById(bookingDto.getVehicleId())
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + bookingDto.getVehicleId()));

        ServiceCenter serviceCenter = centerRepo.findById(bookingDto.getCenterId())
            .orElseThrow(() -> new ResourceNotFoundException("Service Center not found with ID: " + bookingDto.getCenterId()));

        Mechanic mechanic = null;
        if (bookingDto.getUserEmail() != null) {
            mechanic = mechanicRepo.findByAuthEmail(bookingDto.getUserEmail());
            if(mechanic == null)
            	throw new ResourceNotFoundException("Mechanic not found with email: " + bookingDto.getUserEmail());
        }
        Booking booking = BookingMapper.toEntity(bookingDto);

        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setServiceCenter(serviceCenter);
        booking.setMechanic(mechanic);
        
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(Booking.Status.Upcoming);
        booking.setIsVerified(Booking.VerificationStatus.No);

		return brepo.save(booking);
	}


	@Override
	public Booking getBookingDetailsById(int id){
		Booking book = brepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with Id: " + id));
		return book;
	}
	
	@Override
	public boolean cancelBooking(int id) {
		Booking book = brepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with Id: " + id));
         book.setStatus(Booking.Status.Canceled);
         brepo.save(book);
         return true;
	}
	
}