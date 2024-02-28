package com.example.webassignment.event_management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webassignment.event_management.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom queries if needed
}
