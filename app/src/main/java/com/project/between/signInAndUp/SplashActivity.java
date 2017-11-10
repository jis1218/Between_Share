package com.project.between.signInAndUp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.between.AnniversaryAndCalendar.HomeActivity;
import com.project.between.R;
import com.project.between.util.PreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
//                if("true".equals(PreferenceUtil.getStringValue(SplashActivity.this, "autoSignin"))){
//                    String email = PreferenceUtil.getStringValue(SplashActivity.this, "userEmail");
//                    String password = PreferenceUtil.getStringValue(SplashActivity.this, "password");
//                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            SplashActivity.this.finish();
//                        }
//                    });
//                }else{
                    Intent mainIntent = new Intent(SplashActivity.this, SignInActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
//                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
