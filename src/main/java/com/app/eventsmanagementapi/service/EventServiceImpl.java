package com.app.eventsmanagementapi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.app.eventsmanagementapi.dto.EventDto;
import com.app.eventsmanagementapi.dto.Filter;
import com.app.eventsmanagementapi.models.Event;
import com.app.eventsmanagementapi.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }
    @Override
    public boolean updateTicket(Event eventPayload) {
        System.out.println(eventPayload.getId());
        List<Event> events = getAllEvents();
        for(Event event: events) {
            System.out.println("ff");
            if(event.getId() == eventPayload.getId()) {
                System.out.println(event.getId());
                if(event.getFreeSlots() >= 1) {
                    System.out.println(event.getFreeSlots());
                    event.setFreeSlots(event.getFreeSlots() - 1);
                    save(event);
                    System.out.println(event.getFreeSlots());
                    return true;
                }
            }
        }
        return false;
    }

    public List<Event> getFilteredEvents(Filter filter) {
        List<Event> filteredEvents = new ArrayList<Event>();
        List<Event> events = getAllEvents();
        for(Event event: events) {
            if (filter.getCategories().size() != 0) {
                for (String category : filter.getCategories()) {
                    if (category.equals(event.getCategory())) {
                        if (filter.getDates().size() != 0) {
                            for (Date date : filter.getDates()) {
                                if (date.compareTo(event.getEventDate()) == 0) {
                                    filteredEvents.add(event);
                                }
                            }
                        } else {
                            filteredEvents.add(event);
                        }
                    }
                }
           } else {
                for (Date date : filter.getDates()) {
                    if (date.compareTo(event.getEventDate()) == 0) {
                        filteredEvents.add(event);
                    }
                }
            }
        }
        return filteredEvents;
    }
}
