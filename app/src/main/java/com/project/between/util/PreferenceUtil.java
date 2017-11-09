package com.project.between.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 정인섭 on 2017-11-03.
 */
// 로그인이 되면 사용자 정보를 로컬에 저장해 둔다.
// 1. SharedPreferences 를 생성
// 2. 쓰기를 위한 editor 생성
// 3. 키, 값 형태의 값을 저장해 둔다.
public class PreferenceUtil {
    private static final String filename = "FirebaseChatting";

    private static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }
    public static void setValue(Context context, String key, String value){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static void setValue(Context context, String key, long value){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static String getStringValue(Context context, String key){
        return getPreference(context).getString(key, "");
    }

    public static Long getLongValue(Context context, String key){
        return getPreference(context).getLong(key, 0);
    }
}
