package com.epicodus.socialite.models;

/**
 * Created by arbecker on 4/28/16.
 */
public class Person {
    private String mName;
    private String mNumber;


    public Person(String name, String number) {
        this.mName = name;
        this.mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }
}
