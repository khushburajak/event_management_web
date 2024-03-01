package com.example.webassignment.event_management.pojo;

import com.example.webassignment.event_management.entity.Events;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventsPojo {
    private Integer id;
    private MultipartFile photo;
    @NotEmpty(message = "Events Name can't be empty")
    private String name;
    @NotEmpty(message = "Events Quantity can't be empty")
    private String quantity;
    @NotEmpty(message = "Events Price can't be empty")
    private String price;

    public EventsPojo(Events events) {
        this.id = events.getId();
        this.name = events.getName();
        this.quantity = events.getQuantity();
        this.price = events.getPrice();
    }
}
