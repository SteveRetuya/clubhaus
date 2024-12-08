package com.example.clubhaus.admin;

public class Event {
    private String title;
    private String location;
    private String description;
    private int attendees;

    public Event(String title, String location, String description, int attendees) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getAttendees() {
        return attendees;
    }
}
