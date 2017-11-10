package com.project.between.AnniversaryAndCalendar.vo;

/**
 * Created by ZHYUN on 2017-11-10.
 */

public class AnniversaryListVO {
    String key;
    String title;
    String date;
    String dday;
    String homeYn;
    public AnniversaryListVO(String key, String title, String date, String dday, String homeYn){
        this.key = key;
        this.title = title;
        this.date = date;
        this.dday = dday;
        this.homeYn = homeYn;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDday() {
        return dday;
    }

    public String getHomeYn() {
        return homeYn;
    }
}
