package com.example.sbarai.openkart;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Shenee on 2/11/2018.
 */
@IgnoreExtraProperties

public class User {
    String name;

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
