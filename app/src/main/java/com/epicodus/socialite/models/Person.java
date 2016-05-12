package com.epicodus.socialite.models;

/**
 * Created by arbecker on 4/28/16.
 */
public class Person {
    String name;
    String contact;
    String event;
    String pushId;
    String email;
    Boolean isGoing = false;

    public Person(){}


    public Person(String name, String contact, String event) {
        this.name = name;
        this.contact = contact;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
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

    public void confirmInvite() {
        isGoing = true;
    }
}
