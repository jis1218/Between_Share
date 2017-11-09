package com.project.between.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by 정인섭 on 2017-11-03.
 */

public class DialogUtil {

    public static void showDialog(String msg, final Activity activity, final boolean activityFinish){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity); //getBaseContext()는 Context의 요소만 사용, this를 사용하면 Context 밑에 다른 요소들도 사용 가능 (ContextWrapper, Themecontext 등등)
        dialogBuilder.setTitle("알림")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }
}
