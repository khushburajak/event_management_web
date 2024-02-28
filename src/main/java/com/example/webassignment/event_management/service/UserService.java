package com.example.webassignment.event_management.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.webassignment.event_management.model.User;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    User findByUsername(String username);
}
