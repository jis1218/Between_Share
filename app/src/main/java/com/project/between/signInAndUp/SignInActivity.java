package com.project.between.signInAndUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SignInActivity extends AppCompatActivity {

    private Button signUp_btn, signIn_btn;
    private Intent intent, intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn_btn = (Button) findViewById(R.id.signIn_btn);
        signUp_btn = (Button) findViewById(R.id.signUp_btn);


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
}
