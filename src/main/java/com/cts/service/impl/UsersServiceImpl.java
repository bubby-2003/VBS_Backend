package com.cts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.UsersRequestDTO;
import com.cts.entity.Auth;
import com.cts.entity.Customer;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.repository.CustomerRepository;
import com.cts.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	@Autowired
    private final CustomerRepository usersRepository;
	@Autowired
    private final AuthRepository authRepository;

    @Override
    public Customer createProfile(UsersRequestDTO userDto) {
    	
        
        Auth existingAuth = authRepository.findByEmail(userDto.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Email not found in auth table: " + userDto.getEmail()));

      
        Customer user = new Customer();
        user.setAuth(existingAuth);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setPhone(userDto.getPhone()); 
        user.setStatus(userDto.getStatus());

        return usersRepository.save(user);
    }

    @Override
    public Customer updateProfile(int id, UsersRequestDTO updatedUserDto) {
       
        Customer existingUser = usersRepository.findById(id)
        		.orElseThrow(()->new ResourceNotFoundException("User not found with Id: " + id));


        existingUser.setFirstName(updatedUserDto.getFirstName());
        existingUser.setLastName(updatedUserDto.getLastName());
        existingUser.setAddress(updatedUserDto.getAddress());
        existingUser.setPhone(updatedUserDto.getPhone());
        existingUser.setStatus(updatedUserDto.getStatus());

        return usersRepository.save(existingUser);
    }

    @Override
    public Customer viewProfile(int id) {
    	Customer user = usersRepository.findById(id)
        		.orElseThrow(()->new ResourceNotFoundException("User not found with Id: " + id));
        return user;
    }
}