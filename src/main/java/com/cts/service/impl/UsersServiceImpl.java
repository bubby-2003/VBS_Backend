package com.cts.service.impl;

 
import org.springframework.stereotype.Service;

import com.cts.entity.Auth;
import com.cts.entity.Users;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.repository.UsersRepository;
import com.cts.service.UsersService;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
 
    private final UsersRepository usersRepository;
    private final AuthRepository authRepository;
 
    @Override
    public Users createProfile(Users user) {
        Auth existingAuth = authRepository.findById(user.getAuth().getEmail())
        		.orElseThrow(()->new ResourceNotFoundException("Email not found in auth table: " + user.getAuth().getEmail()));

        user.setAuth(existingAuth);
        return usersRepository.save(user);
    }
 
    @Override
    public Users updateProfile(String email, Users updatedUser) {
        Users existingUser = usersRepository.findByAuthEmail(email);
              //  .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//
//        String existingEmail = existingUser.getAuth().getEmail();
//        String incomingEmail = updatedUser.getAuth().getEmail();
//
//        // Check if incoming email is different
//        if (!existingEmail.equals(incomingEmail)) {
//            throw new RuntimeException("Email update is not allowed. Existing email: " + existingEmail);
//        }

        // Validate that the email exists in Auth table
     //   authRepository.findById(incomingEmail); // Will throw ResourceNotFoundException if not found

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setStatus(updatedUser.getStatus());

        return usersRepository.save(existingUser);
    }

 
    @Override
    public Users viewProfile(String email) {
        return usersRepository.findByAuthEmail(email);
    }

}
 