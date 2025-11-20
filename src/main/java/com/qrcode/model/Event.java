package com.qrcode.model;

public class Event {
    private int eventId;
    private String title;
    private String description;
    private String eventDate;
    private String eventTime;
    private String venue;
    private String organizer;
    private int createdBy;
    private String createdAt;
    
    // Constructors
    public Event() {}
    
    public Event(int eventId, String title, String description, String eventDate, String eventTime, 
                 String venue, String organizer, int createdBy, String createdAt) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.organizer = organizer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getEventId() {
        return eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getEventDate() {
        return eventDate;
    }
    
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    
    public String getEventTime() {
        return eventTime;
    }
    
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    
    public String getVenue() {
        return venue;
    }
    
    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    public String getOrganizer() {
        return organizer;
    }
    
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", venue='" + venue + '\'' +
                ", organizer='" + organizer + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}