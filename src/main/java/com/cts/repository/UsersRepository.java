package com.cts.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.entity.Auth;
import com.cts.entity.Users;
 
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Users findByAuthEmail(String email);
}