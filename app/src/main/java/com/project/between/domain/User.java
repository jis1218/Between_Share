package com.project.between.domain;

/**
 * Created by 정인섭 on 2017-11-03.
 */

public class User {
    public String fId;
    public String mName;
    public String email;
    public String token;
    public String phone_num;
    public String mId;


    public User(){
        // default for firebase
    }

    public User(String fId, String mName, String email, String phone_num, String mId) {
        this.fId = fId;
        this.mName = mName;
        this.email = email;
        this.phone_num = phone_num;
        this.mId = mId;
    }
}
