package com.epicodus.socialite.models;

/**
 * Created by arbecker on 5/4/16.
 */
public class User {
    String name;
    String email;
    String userId;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
}