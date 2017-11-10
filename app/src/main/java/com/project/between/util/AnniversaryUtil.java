package com.project.between.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ZHYUN on 2017-11-07.
 */

public class AnniversaryUtil {
    // D-day 계산
    public static int getDday(String getDate){
//        Step1. 현재 시간 가져오기.
//        long now = System.currentTimeMillis();

//        Step2. Date 생성하기
//        Date date = new Date(now);

//        Step3. 가져오고 싶은 형식으로 가져오기
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String getTime = sdf.format(date);

        int mYear=0;
        int mMonth=0;
        int mDay=0;
        String[] temp = getDate.split("년");
        mYear   = Integer.parseInt(temp[0].trim());
        temp    = temp[1].split("월");
        mMonth  = Integer.parseInt(temp[0].trim());
        temp    = temp[1].split("일");
        mDay    = Integer.parseInt(temp[0].trim());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
        Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

        mMonth -= 1; // 받아온날자에서 -1을 해줘야함.
        ddayCal.set(mYear,mMonth,mDay);// D-day의 날짜를 입력
        Log.e("테스트",simpleDateFormat.format(todaCal.getTime()) + "");
        Log.e("테스트",simpleDateFormat.format(ddayCal.getTime()) + "");

        long today = todaCal.getTimeInMillis()/86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
        long dday = ddayCal.getTimeInMillis()/86400000;
        long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.

        if(count > -1) count++;
        return (int) count;

    }

//    Date 출력
}
