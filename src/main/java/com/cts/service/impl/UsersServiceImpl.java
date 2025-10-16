package com.cts.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cts.dto.UsersRequestDTO;
import com.cts.dto.UsersResponseDTO;
import com.cts.entity.Auth;
import com.cts.entity.Customer;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.repository.CustomerRepository;
import com.cts.service.UsersService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final CustomerRepository usersRepository;
    private final AuthRepository authRepository;
    private final ModelMapper modelMapper;

    @Override
    public UsersResponseDTO createProfile(UsersRequestDTO userDto) {
        Auth existingAuth = authRepository.findByEmail(userDto.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Email not found in auth table: " + userDto.getEmail()));

        Customer user = modelMapper.map(userDto, Customer.class);
        user.setAuth(existingAuth);

        Customer savedUser = usersRepository.save(user);
        return modelMapper.map(savedUser, UsersResponseDTO.class);
    }

    @Override
    public UsersResponseDTO updateProfile(int id, UsersRequestDTO updatedUserDto) {
        Customer existingUser = usersRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + id));

        modelMapper.map(updatedUserDto, existingUser);
        Customer updatedUser = usersRepository.save(existingUser);
        return modelMapper.map(updatedUser, UsersResponseDTO.class);
    }

    @Override
    public UsersResponseDTO viewProfile(int id) {
        Customer user = usersRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + id));

        return modelMapper.map(user, UsersResponseDTO.class);
    }
}
