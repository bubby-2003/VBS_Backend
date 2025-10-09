package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.entity.Users;
 
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Users findByAuthEmail(String email);
}