package com.example.webassignment.event_management.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webassignment.event_management.entity.EventCart;
import com.example.webassignment.event_management.entity.Events;
import com.example.webassignment.event_management.entity.Queries;
import com.example.webassignment.event_management.entity.User;
import com.example.webassignment.event_management.pojo.EventsPojo;
import com.example.webassignment.event_management.service.EventCartService;
import com.example.webassignment.event_management.service.EventsService;
import com.example.webassignment.event_management.service.QueryService;
import com.example.webassignment.event_management.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final EventsService eventsService;
    private final EventCartService eventCartService;
    private final QueryService queryService;
    @GetMapping("/order-list")
    public String getOrderListPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        assert principal != null;
        Integer id = userService.findByEmail(principal.getName()).getId();
        List<EventCart> list = eventCartService.fetchAll(id);
        model.addAttribute("cartItems", list);
        return "order_list";
    }
    @GetMapping("/user-list")
    public String getUserListPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        List<User> users = userService.fetchAll();
        model.addAttribute("userlist", users.stream().map(user ->
                User.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .number(user.getNumber())
                        .address(user.getAddress())
                        .build()
        ));
        return "userlist";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, Principal principal) {
        userService.deleteById(id);
        return "redirect:/admin/user-list";
    }
    @GetMapping("/add-events")
    public String getAddEventPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        model.addAttribute("events", new EventsPojo());
        return "add_events";
    }
    @GetMapping("/events-list")
    public String getProductList(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        List<Events> event = eventsService.fetchAll();
        model.addAttribute("event", event);
        return "eventslist";
    }
    @GetMapping("/editevents/{id}")
    public String editProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        Events events = eventsService.fetchById(id);
        model.addAttribute("events", new EventsPojo(events));
        return "add_events";
    }
    @GetMapping("/deleteEvents/{id}")
    public String deleteProducts(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        eventsService.deleteById(id);
        return "redirect:/admin/events-list";
    }
    @PostMapping("/save/events")
    public String saveEvents(@Valid EventsPojo eventsPojo) throws IOException {
        eventsService.save(eventsPojo);
        return "redirect:/dashboard";
    }
    @GetMapping("/queries")
    public String getQueryPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        List<Queries> queries = queryService.fetchAll();
        model.addAttribute("queries", queries);
        return "query_section";
    }

    @GetMapping("/settings")
    public String getAdminSettingsPage(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
        return "admin_settings";
    }
    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/khushbuevents/";
        File file = new File(filePath + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }
}
