package com.project.between.AnniversaryAndCalendar.vo;

/**
 * Created by ZHYUN on 2017-11-09.
 */

public class AlarmReceiveUser {
    String receive_user1;
    String receive_user2;
    public AlarmReceiveUser(String receive_user1, String receive_user2){
        this.receive_user1 = receive_user1;
        this.receive_user2 = receive_user2;
    }
    public AlarmReceiveUser(String receive_user1){
        this.receive_user1 = receive_user1;
    }
}
