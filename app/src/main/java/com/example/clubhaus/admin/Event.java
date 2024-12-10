package com.example.clubhaus.admin;

public class Event {
    private String title;
    private String location;
    private String description;
    private String interests;
    private String[] date;
    private String[] time;
    private int attendees;
    private String imageLink;

    public Event(String title, String location, String description, String url, int attendees) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
        imageLink = url;
    }

    public Event(String title, String location, String description, int attendees) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
    }

    public Event(String title, String location, String description, int attendees, String interests) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
        this.interests = interests;
    }

    public Event(String title, String location, String description, int attendees, String interests, String[] date, String[] time) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
        this.interests = interests;
        this.date = date;
        this.time = time;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public void setTime(String[] time) {
        this.time = time;
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

    public String getInterests() {
        return interests;
    }

    public String[] getDate() {
        return date;
    }

    public String[] getTime() {
        return time;
    }

    public String getImageLink() {
        return imageLink;
    }
}
