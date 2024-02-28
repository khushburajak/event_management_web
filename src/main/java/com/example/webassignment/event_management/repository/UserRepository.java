package com.example.webassignment.event_management.repository;


import com.example.webassignment.event_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    // You can add more custom queries if needed
}
