package com.example.webassignment.event_management.service.impl;

import com.example.webassignment.event_management.entity.Events;
import com.example.webassignment.event_management.pojo.EventsPojo;
import com.example.webassignment.event_management.repo.EventCartRepo;
import com.example.webassignment.event_management.repo.EventsRepo;
import com.example.webassignment.event_management.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {
    public final EventsRepo eventsRepo;
    public final EventCartRepo eventCartRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/tintinevents";
    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/tintinevents/";
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


    @Override
    public String save(EventsPojo eventsPojo) throws IOException {
        Events events;
        if (eventsPojo.getId() != null) {
            events = eventsRepo.findById(eventsPojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            events = new Events();
        }
        if(eventsPojo.getId()!=null){
            events.setId(eventsPojo.getId());
        }
        events.setName(eventsPojo.getName());
        events.setQuantity(eventsPojo.getQuantity());
        events.setPrice(eventsPojo.getPrice());
        if(eventsPojo.getPhoto()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, eventsPojo.getPhoto().getOriginalFilename());
            fileNames.append(eventsPojo.getPhoto().getOriginalFilename());
            Files.write(fileNameAndPath, eventsPojo.getPhoto().getBytes());

            events.setPhoto(eventsPojo.getPhoto().getOriginalFilename());
        }

        eventsRepo.save(events);
        return "created";
    }

    @Override
    public List<Events> fetchAll() {
        return findAllInList(eventsRepo.findAll());
    }

    private List<Events> findAllInList(List<Events> list) {
        Stream<Events> allCart=list.stream().map(events ->
                Events.builder()
                        .id(events.getId())
                        .imageBase64(getImageBase64(events.getPhoto()))
                        .name(events.getName())
                        .quantity(events.getQuantity())
                        .price(events.getPrice())
                        .build()
        );

        list = allCart.toList();
        return list;
    }

    @Override
    public Events fetchById(Integer id) {
        return eventsRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
    }

    @Override
    public void deleteById(Integer id) {
        eventsRepo.deleteById(id);
    }


  

}
