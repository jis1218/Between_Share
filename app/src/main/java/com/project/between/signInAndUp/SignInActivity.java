package com.project.between.signInAndUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.project.between.R;


public class SignInActivity extends AppCompatActivity {

    private Button signUp_btn, signIn_btn;
    private Intent intent, intent2;

    ViewFlipper viewFlipper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initview();
        viewFlipperFunction();


        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignInActivity.this, SignIn2Activity.class);
                startActivity(intent);

            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2 = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent2);

            }
        });


    }

    private void viewFlipperFunction() {
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.in_anim));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.out_anim));

        Log.e("viewFlipper", "계속 진행중!");
    }


    private void initview() {
        signIn_btn = (Button) findViewById(R.id.signIn_btn);
        signUp_btn = (Button) findViewById(R.id.signUp_btn);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFilpper);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewFlipper.stopFlipping();
        Log.e("viewFlipper", "멈춤!");
    }
}
