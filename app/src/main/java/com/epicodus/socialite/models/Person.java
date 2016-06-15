package com.epicodus.socialite.models;

/**
 * Created by arbecker on 4/28/16.
 */
public class Person {
    String name;
    String phone;
    String event;
    String pushId;
    String email;
    Boolean isGoing = false;
    String eventId;

    public Person(){}

    public Person(String name, String event) {
        this.name = name;
        this.event = event;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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

    public void confirmInvite() {
        isGoing = true;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
