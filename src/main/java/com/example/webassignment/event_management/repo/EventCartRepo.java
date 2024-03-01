package com.example.webassignment.event_management.repo;

import com.example.webassignment.event_management.entity.EventCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventCartRepo extends JpaRepository<EventCart, Integer> {
    @Query(value = "SELECT * FROM EventsCARTS WHERE user_id = ?1", nativeQuery = true)
    Optional<List<EventCart>> fetchAll(Integer userId);
}
