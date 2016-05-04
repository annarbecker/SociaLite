package com.epicodus.socialite.models;

/**
 * Created by arbecker on 4/28/16.
 */
public class Person {
    public String name;
    public String number;
    public String event;


    public Person(String name, String number, String event) {
        this.name = name;
        this.number = number;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEvent() {
        return event;
    }
}
