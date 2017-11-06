package com.project.between.Util;

import java.text.SimpleDateFormat;

/**
 * Created by 정인섭 on 2017-11-06.
 */

public class TimeConverter {
    public static String timeConverter(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }
}
