package com.example.webassignment.event_management.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webassignment.event_management.entity.EventCart;
import com.example.webassignment.event_management.pojo.EventCartPojo;
import com.example.webassignment.event_management.service.EventCartService;
import com.example.webassignment.event_management.service.EventsService;
import com.example.webassignment.event_management.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final EventsService eventsService;

    private final UserService userService;
    private final EventCartService eventsCartService;

    @GetMapping()
    public String displayCart(Principal principal, Model model, EventCartPojo eventsCartPojo){
        Integer id = userService.findByEmail(principal.getName()).getId();
        List<EventCart> list = eventsCartService.fetchAll(id);
        model.addAttribute("cart", eventsCartPojo);
        model.addAttribute("cartItems", list);
        return "cart";
    }

    @PostMapping("/updateQuantity/{id}")
    public String updateQuantity(@Valid EventCartPojo eventsCartPojo){
        EventCart eventsCart = eventsCartService.fetchOne(eventsCartPojo.getId());
        eventsCartService.updateQuantity(eventsCart);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String deleteCartItem(@PathVariable("id") Integer id){
        eventsCartService.deleteFromCart(id);
        return "redirect:/cart";
    }
}