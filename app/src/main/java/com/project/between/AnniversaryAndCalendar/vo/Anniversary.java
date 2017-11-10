package com.project.between.AnniversaryAndCalendar.vo;

/**
 * Created by ZHYUN on 2017-11-06.
 */

public class Anniversary {
    String title;
    String anniType;
    String homeYn;
    String date;
    String dayCountOneYn;
    String repeatYN;
    String choiceAlram;
    String memo;
    String reg_date;
    String reg_time;
    AlarmReceiveUser alarmReceiveUser;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnniType() {
        return anniType;
    }

    public void setAnniType(String anniType) {
        this.anniType = anniType;
    }

    public String getHomeYn() {
        return homeYn;
    }

    public void setHomeYn(String homeYn) {
        this.homeYn = homeYn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayCountOneYn() {
        return dayCountOneYn;
    }

    public void setDayCountOneYn(String dayCountOneYn) {
        this.dayCountOneYn = dayCountOneYn;
    }

    public String getRepeatYN() {
        return repeatYN;
    }

    public void setRepeatYN(String repeatYN) {
        this.repeatYN = repeatYN;
    }

    public String getChoiceAlram() {
        return choiceAlram;
    }

    public void setChoiceAlram(String choiceAlram) {
        this.choiceAlram = choiceAlram;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public AlarmReceiveUser getAlarmReceiveUser() {
        return alarmReceiveUser;
    }

    public void setAlarmReceiveUser(AlarmReceiveUser alarmReceiveUser) {
        this.alarmReceiveUser = alarmReceiveUser;
    }
}
