package com.example.webassignment.event_management.service.impl;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.webassignment.event_management.entity.EventCart;
import com.example.webassignment.event_management.pojo.EventCartPojo;
import com.example.webassignment.event_management.repo.EventCartRepo;
import com.example.webassignment.event_management.repo.EventsRepo;
import com.example.webassignment.event_management.repo.UserRepo;
import com.example.webassignment.event_management.service.EventCartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventsCartServiceImpl implements EventCartService {
    private final UserRepo userRepo;
    private final EventCartRepo eventCartRepo;
    private final EventsRepo eventsRepo;

    @Override
    public List<EventCart> fetchAll() {
        return this.eventCartRepo.findAll();
    }

    @Override
    public EventCartPojo save(EventCartPojo eventsCartPojo) {
        EventCart eventsCart = new EventCart();
        if(eventsCartPojo.getId()!=null){
            eventsCart.setId(eventsCartPojo.getId());
        }
        eventsCart.setEvents(eventsRepo.findById(eventsCartPojo.getEvents_id()).orElseThrow());
        eventsCart.setUser(userRepo.findById(eventsCartPojo.getUser_id()).orElseThrow());
        eventCartRepo.save(eventsCart);
        return new EventCartPojo();
    }

    @Override
    public EventCart fetchOne(Integer id) {
        return eventCartRepo.findById(id).orElseThrow();
    }

    @Override
    public void deleteFromCart(Integer id) {
        eventCartRepo.deleteById(id);
    }

    @Override
    public String updateQuantity(EventCart eventsCart) {
        eventCartRepo.save(eventsCart);
        return "Updated";
    }

    @Override
    public List<EventCart> fetchAll(Integer id) {
        return findAllInList(eventCartRepo.findAll());
    }
    public List<EventCart> findAllInList(List<EventCart> list){
        Stream<EventCart> allCart=list.stream().map(productCart ->
                EventCart.builder()
                        .id(productCart.getId())
                        .events(productCart.getEvents())
                        .user(productCart.getUser())
                        .build()
        );

        list = allCart.toList();
        return list;
    }

  

  

   
}
