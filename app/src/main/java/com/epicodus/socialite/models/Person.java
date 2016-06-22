package com.epicodus.socialite.models;

/**
 * Created by arbecker on 4/28/16.
 */

public class Person {
    String name;
    String phone;
    String event;
    String RSVP;
    String rsvp;
    String email;

    String pushId;

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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRSVP() {
        return RSVP;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
