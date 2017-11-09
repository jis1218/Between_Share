package com.project.between.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 정인섭 on 2017-11-03.
 */

public class VerificationUtil {

    public static boolean isValidEmail(String email) {
        String regex = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);

        return m.matches();
    }


    public static boolean isValidPassword(String password) {
        // 영문자와 숫자만 허용
        String regex = "^[0-9A-Za-z]{8,14}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static boolean isValidName(String name){
        // 영문자와 숫자만 허용
        String regex = "^[가-힣A-Za-z]{2,10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);

        return m.matches();
    }
}
