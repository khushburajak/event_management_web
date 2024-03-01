package com.example.webassignment.event_management.repo;

import com.example.webassignment.event_management.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsRepo extends JpaRepository<Events, Integer> {
    @Query(value = "select * from events where name=?1", nativeQuery = true)
    Optional<Events> findBybook_id(String name);
}
