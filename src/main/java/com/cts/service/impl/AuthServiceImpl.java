package com.cts.service.impl;
 
import com.cts.entity.Auth;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public class AuthServiceImpl implements AuthService {
 
    @Autowired
    private AuthRepository authRepository;
 
    @Override
    public List<Auth> getAll() {
        return authRepository.findAll();
    }
 
    @Override
    public Auth getByEmail(String email) {
        return authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
    }
 
    @Override
    public String create(Auth auth) {			
        try {
            if (authRepository.existsById(auth.getEmail())) {
                return "Registration failed: Email already exists";
            }
            authRepository.save(auth);
            return "Registered successfully";
        } catch (Exception e) {
            return "Unable to register: " + e.getMessage();
        }
    }
 
    @Override
    public Auth update(String email, Auth updatedAuth) {
        Auth existing = getByEmail(email);
        existing.setPassword(updatedAuth.getPassword());
        existing.setRole(updatedAuth.getRole());
        return authRepository.save(existing);
    }
 
    @Override
    public void delete(String email) {
        Auth existing = getByEmail(email);
        authRepository.delete(existing);
    }
 
    @Override
    public String login(String email, String password, String role) {
        try {
            Auth auth = authRepository.findByEmailAndPasswordAndRole(email, password, role)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));
            return "Login successful for role: " + auth.getRole();
        } catch (ResourceNotFoundException e) {
            return "Invalid credentials";
        } catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }
}