package com.example.sbarai.openkart.Models;

/**
 * Created by sbarai on 1/18/18.
 */

public class User {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    String userId;
    String name;
    String number;

}
