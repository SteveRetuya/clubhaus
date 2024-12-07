package com.example.clubhaus;

public class User {
    private String username, birthday, password;

    public User(String username, String birthday, String password) {
        this.username = username;
        this.birthday = birthday;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }
}
