package com.example.webassignment.event_management.service;



import java.io.IOException;
import java.util.List;

import com.example.webassignment.event_management.entity.Events;
import com.example.webassignment.event_management.pojo.EventsPojo;

public interface EventsService {

    String save(EventsPojo EeventsPojo) throws IOException;

    List<Events> fetchAll();

    Events fetchById(Integer id);

    void deleteById(Integer id);

}
