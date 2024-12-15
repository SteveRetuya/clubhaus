package com.example.clubhaus;

public class User {
    private String username;
    private String password;
    private String email;
    private String imageURL;

    public User(String username, String password, String email, String imageURL) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getImageURL() {
        return imageURL;
    }
}
