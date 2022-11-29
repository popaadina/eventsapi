package com.app.eventsmanagementapi.service;

import java.util.List;
import java.util.Optional;

import com.app.eventsmanagementapi.dto.Filter;
import com.app.eventsmanagementapi.models.Event;

public interface EventService {
    List<Event> getAllEvents();
    Optional<Event> findById(long id);
    Event save(Event event);

    boolean updateTicket(Event event);

    List<Event> getFilteredEvents(Filter filter);
}
