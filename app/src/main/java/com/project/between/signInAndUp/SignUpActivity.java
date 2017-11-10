package com.project.between.signInAndUp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.project.between.util.DialogUtil;
import com.project.between.util.PreferenceUtil;
import com.project.between.util.VerificationUtil;
import com.project.between.R;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    private EditText signUp_email_edit;
    private EditText signUp_password_edit;
    private Button signUp_btn;
    String tempKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        initView();
    }

    public void signup(View view) {
        final String email = signUp_email_edit.getText().toString();
        final String password = signUp_password_edit.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "확인", Toast.LENGTH_SHORT).show();
                            FirebaseUser fUser = auth.getCurrentUser();
//                            UserProfileChangeRequest.Builder profile = new UserProfileChangeRequest.Builder();
//                            profile.setDisplayName(email);
//                            fUser.updateProfile(profile.build());

                            fUser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            DialogUtil.showDialog("이메일을 발송하였습니다.", SignUpActivity.this, true);
                                            moveToNext();
                                            PreferenceUtil.setValue(SignUpActivity.this, "userEmail", email);
                                            PreferenceUtil.setValue(SignUpActivity.this, "password", password);
                                            PreferenceUtil.setValue(SignUpActivity.this, "autoSignin", "true");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    DialogUtil.showDialog("오류발생" + e.getMessage(), SignUpActivity.this, false);
                                }
                            });
                            tempKey = email.replace(".", "_");
                            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            User user = new User(fUser.getUid(), email, refreshedToken);
                            userRef.child(tempKey).setValue(user);


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "확인 안됨 : " + e.toString(), Toast.LENGTH_SHORT).show();
                Log.e("로그", e.toString());

            }
        });
    }

    public void moveToNext() {
        Intent intent = new Intent(SignUpActivity.this, PhoneConnectActivity.class);
        intent.putExtra("tempKey", tempKey);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    boolean checkEmail = false;
    boolean checkPassword = false;

    private void enableSignupButton() {
        if (checkEmail && checkPassword) {
            signUp_btn.setEnabled(true);
        } else {
            signUp_btn.setEnabled(false);
        }
    }

    private void initView() {
        signUp_email_edit = (EditText) findViewById(R.id.signUp_email_edit);
        signUp_email_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEmail = VerificationUtil.isValidEmail(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUp_password_edit = (EditText) findViewById(R.id.signUp_password_edit);
        signUp_password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPassword = VerificationUtil.isValidPassword(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUp_btn = (Button) findViewById(R.id.signUp_btn);
    }
}
























