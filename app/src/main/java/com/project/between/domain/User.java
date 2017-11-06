package com.project.between.domain;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-06.
 */

public class User {
    String name;
    String email;
    String password;
    String phone_number;
    String profile_picture;
    String token;
    String notification;
    String birthday;
    String join_date;

    public User() {
    }

    public User(String name, String email, String password, String phone_number, String profile_picture,
                String token, String notification, String birthday, String join_date) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.profile_picture = profile_picture;
        this.token = token;
        this.notification = notification;
        this.birthday = birthday;
        this.join_date = join_date;
    }
}
