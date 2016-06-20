package com.epicodus.socialite.models;

/**
 * Created by arbecker on 4/28/16.
 */
public class Person {
    String name;
    String phone;
    String event;
    String email;
    String RSVP;
    String rsvp;

    public Person(){}

    public Person(String name, String event, String RSVP) {
        this.name = name;
        this.event = event;
        this.RSVP = RSVP;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEvent() {
        return event;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRSVP() {
        return RSVP;
    }

}
