package com.app.eventsmanagementapi.rest;

import java.util.List;
import java.util.Optional;

import com.app.eventsmanagementapi.dto.EventDto;
import com.app.eventsmanagementapi.dto.Filter;
import com.app.eventsmanagementapi.models.Event;
import com.app.eventsmanagementapi.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/events")
public class EventController {

    @Autowired
    public EventService eventService;

    @GetMapping
    List<Event> getAll() {
        return eventService.getAllEvents();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Event> getById(@PathVariable @Min(1) long id) {
        Optional<Event> event = eventService.findById(id);
        return new ResponseEntity<>(event.get(), HttpStatus.OK);
    }


    @GetMapping("/filtered")
    List<Event> getFilteredEvents(@RequestBody Filter filter) {
       return eventService.getFilteredEvents(filter);
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {
        eventService.save(event);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateEvent(@RequestBody Event eventPayload) {
          if(eventService.updateTicket(eventPayload)) {
              return new ResponseEntity(HttpStatus.OK);
        } else {
              return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }



}
