package com.example.webassignment.event_management.pojo;

import com.example.webassignment.event_management.entity.EventCart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCartPojo {
    private Integer id;

    private Integer user_id;
    private Integer events_id;

    public EventCartPojo(EventCart eventsCart) {
        this.id = eventsCart.getId();
        this.user_id = eventsCart.getUser().getId();
        this.events_id = eventsCart.getEvents().getId();
    }
}
