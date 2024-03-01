package com.example.webassignment.event_management.repo;

import com.example.webassignment.event_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM USERS WHERE email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    User findByEmailAndOTP(String email, String otp);
}
