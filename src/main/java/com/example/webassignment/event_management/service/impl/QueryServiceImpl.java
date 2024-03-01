package com.example.webassignment.event_management.service.impl;

import com.example.webassignment.event_management.entity.Queries;
import com.example.webassignment.event_management.pojo.QueriesPojo;
import com.example.webassignment.event_management.repo.QueriesRepo;
import com.example.webassignment.event_management.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
    private final QueriesRepo queriesRepo;

    @Override
    public List<Queries> fetchAll() {
        return this.queriesRepo.findAll();
    }

    @Override
    public String save(QueriesPojo queriesPojo) {
        Queries queries=new Queries();
        queries.setName(queriesPojo.getName());
        queries.setEmail(queriesPojo.getEmail());
        queries.setSubject(queriesPojo.getSubject());
        queries.setMessage(queriesPojo.getMessage());
        queriesRepo.save(queries);
        return "saved";
    }
}
