package com.example.webassignment.event_management.service;



import java.util.List;

import com.example.webassignment.event_management.entity.EventCart;
import com.example.webassignment.event_management.pojo.EventCartPojo;

public interface EventCartService {
    List<EventCart> fetchAll();

    EventCartPojo save(EventCartPojo eventCartPojo);

    EventCart fetchOne(Integer id);

    void deleteFromCart(Integer id);

    String updateQuantity(EventCart eventCart);

    List<EventCart> fetchAll(Integer id);
}
