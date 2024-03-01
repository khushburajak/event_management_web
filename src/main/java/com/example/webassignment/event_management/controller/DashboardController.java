package com.example.webassignment.event_management.controller;

import com.example.webassignment.event_management.entity.Events;
import com.example.webassignment.event_management.pojo.EventCartPojo;
import com.example.webassignment.event_management.service.EventCartService;
import com.example.webassignment.event_management.service.EventsService;
import com.example.webassignment.event_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor //creates the constructor with all required arguments
@RequestMapping("/dashboard")
public class DashboardController {
    private final EventsService eventsService;
    private final EventCartService eventsCartService;
    private final UserService userService;
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
    @GetMapping("")
    public String getDashboard(Model model, Principal principal, Authentication authentication){
        if (authentication!=null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("Admin")) {
                    return "redirect:/admin/order-list";
                }
            }
        }
        List<Events> events = eventsService.fetchAll();
        if (principal!=null) {
            model.addAttribute("info", userService.findByEmail(principal.getName()));
        }
//        model.addAttribute("eventss", events.stream().map(eventss ->
//                        eventss.builder()
//                                .id(eventss.getId())
//                                .imageBase64(getImageBase64(eventss.getPhoto()))
//                                .name(eventss.getName())
//                                .quantity(eventss.getQuantity())
//                                .price(eventss.getPrice())
//                                .build()
//                )
//        );
        model.addAttribute("events", events);
        model.addAttribute("savecarts", new EventCartPojo());
        return "dashboard";
    }

    @PostMapping("/save")
    public String savecart(@Valid EventCartPojo eventsCartPojo) {
        eventsCartService.save(eventsCartPojo);
        return "redirect:/login";
    }
    @GetMapping("/aboutus")
    public String getAboutUsPage() {
        return "aboutus";
    }
}
