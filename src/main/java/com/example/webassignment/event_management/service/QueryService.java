package com.example.webassignment.event_management.service;



import java.util.List;

import com.example.webassignment.event_management.entity.Queries;
import com.example.webassignment.event_management.pojo.QueriesPojo;

public interface QueryService {
    List<Queries> fetchAll();

    String save(QueriesPojo queriesPojo);
}
