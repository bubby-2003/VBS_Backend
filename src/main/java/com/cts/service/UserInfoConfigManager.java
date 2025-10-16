package com.cts.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cts.entity.Auth;
import com.cts.repository.AuthRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoConfigManager implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(auth.getEmail())
                .password(auth.getPassword())
                .roles(auth.getRole().name())
                .build();
    }
}
