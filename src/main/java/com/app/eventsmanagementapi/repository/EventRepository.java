package com.app.eventsmanagementapi.repository;

import com.app.eventsmanagementapi.models.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
