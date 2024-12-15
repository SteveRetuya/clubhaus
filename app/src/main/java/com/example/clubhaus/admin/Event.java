package com.example.clubhaus.admin;

import android.widget.ImageView;

import java.util.List;

public class Event {
    private String title;
    private String location;
    private String description;
    private String interests;
    private String[] date;
    private String[] time;

    private List<String> date_List;
    private List<String> time_List;
    private ImageView imageUrl;
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

    public Event(String title, String location, String description, int attendees, String interests, List<String> date, List<String> time) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
        this.interests = interests;
        this.date_List = date;
        this.time_List = time;
    }

    public Event(String title, String location, String description, int attendees, String interests, String[] date, String[] time, ImageView imageUrl) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
        this.interests = interests;
        this.date = date;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public Event(String title, String location, String description, int attendees, String interests, List<String> date, List<String> time, String imageUrl) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.attendees = attendees;
        this.interests = interests;
        this.date_List = date;
        this.time_List = time;
        imageLink = imageUrl;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public void setImageUrl(ImageView imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate_List(List<String> date_List) {
        this.date_List = date_List;
    }

    public void setTime_List(List<String> time_List) {
        this.time_List = time_List;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public ImageView getImageUrl() {
        return imageUrl;
    }

    public List<String> getDate_List() {
        return date_List;
    }

    public List<String> getTime_List() {
        return time_List;
    }

    public String getImageLink() {
        return imageLink;
    }
}
