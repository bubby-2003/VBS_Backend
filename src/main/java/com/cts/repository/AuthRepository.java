package com.cts.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByEmail(String email);

	
}