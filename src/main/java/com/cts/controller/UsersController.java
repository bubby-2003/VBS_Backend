package com.cts.controller;

import com.cts.dto.UsersDTO;
import com.cts.entity.Auth;
import com.cts.entity.Users;
import com.cts.mapper.UsersMapper;
import com.cts.service.AuthService;
import com.cts.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;


    @PostMapping("/create")
    public UsersDTO createProfile(@RequestBody Users user) {
        Users savedUser = usersService.createProfile(user);
        return UsersMapper.toDTO(savedUser);
    }

    @PutMapping("/{id}")
    public UsersDTO updateProfile(@PathVariable Integer id, @RequestBody Users user) {
        Users updatedUser = usersService.updateProfile(id, user);
        return UsersMapper.toDTO(updatedUser);
    }

    @GetMapping("/{email}")
    public UsersDTO viewProfile(@PathVariable String email) {
        Users user = usersService.viewProfile(email);
        return UsersMapper.toDTO(user);
    }
}
