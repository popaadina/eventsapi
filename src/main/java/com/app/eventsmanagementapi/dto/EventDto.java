package com.app.eventsmanagementapi.dto;

import java.time.Instant;
import java.util.Date;

public class EventDto {
    private Long id;
    private String title;
    private String content;
    private Instant createdOn;
    private Date eventDate;
    private String image;
    private String username;
    private int category;
    private String price;

    public Number getSlots() {
        return slots;
    }

    public void setSlots(Number slots) {
        this.slots = slots;
    }

    public Number getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(Number freeSlots) {
        this.freeSlots = freeSlots;
    }

    private Number slots;

    private Number freeSlots;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
