package com.project.between.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 정인섭 on 2017-11-06.
 */

public class TimeConverter {
    public static String timeConverterMinute(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREAN);
        return sdf.format(time);
    }

    public static String timeConverterDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);
        return sdf.format(time);
    }


}
