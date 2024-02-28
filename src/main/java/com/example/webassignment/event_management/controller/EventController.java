package com.example.webassignment.event_management.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.webassignment.event_management.model.Event;
import com.example.webassignment.event_management.service.EventService;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public String showEventList(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "event/eventList";
    }

    @GetMapping("/add")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event/addEvent";
    }

    @PostMapping("/add")
    public String addEvent(@ModelAttribute("event") Event event) {
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/view/{id}")
    public String showEventDetails(@PathVariable("id") Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        return "event/viewEvent";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

    @GetMapping("/login")
public String showLoginForm() {
    return "login";
}

@GetMapping("/register")
public String showRegistrationForm() {
    return "register";
}


    @GetMapping("/profile")
    public ModelAndView showUserProfile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("username", principal.getName());
        // Add additional profile information as needed
        return modelAndView;
    }
}
