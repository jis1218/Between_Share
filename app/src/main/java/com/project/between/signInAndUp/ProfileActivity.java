package com.project.between.signInAndUp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.between.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.between.AnniversaryAndCalendar.HomeActivity;
import com.project.between.chatting.ChattingActivity;
import com.project.between.util.PreferenceUtil;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    Button start_btn;
    Calendar calendar;
    TextView textViewBirthday, textViewFirstday;
    EditText name_edit;
    RadioGroup radiogender;
    RadioButton radiomale, radiofemale;
    int year, month, day;
    int selectedId;
    String birthday, firstday;
    String tempkey;
    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference photoRef;
    StorageReference storageRef;
    String gender;
    String name;
    Uri downloadUrl;
    ImageView imageProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        String photoRoom = PreferenceUtil.getStringValue(this, "photoroom");
        String myNum = PreferenceUtil.getStringValue(this, "myNum");
        photoRef = database.getReference("photo").child(photoRoom).child("profile").child(myNum);
        storageRef = FirebaseStorage.getInstance().getReference();

        getIntentFromPhoneConnectActivity();

        init();


        textViewBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        textViewBirthday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        birthday = textViewBirthday.getText().toString();
                    }
                }, year, month, day).show();
            }
        });

        textViewFirstday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        textViewFirstday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        firstday = textViewFirstday.getText().toString();
                    }
                }, year, month, day).show();
            }
        });

    }

    public void movetohome(View view) {
        addDatabase();

        Intent intentForHome = new Intent(ProfileActivity.this, HomeActivity.class);
        intentForHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentForHome.putExtra("profileImg", downloadUrl.toString());
        photoRef.setValue(downloadUrl.toString());
        startActivity(intentForHome);
        Intent intentForChat = new Intent(ProfileActivity.this, ChattingActivity.class);
        intentForChat.putExtra("tempkey", tempkey);
        startActivity(intentForChat);

    }

    public void addDatabase() {
        selectedId = radiogender.getCheckedRadioButtonId();
       gender = selectedId == R.id.radiomale ? "male" : "female";
        name = name_edit.getText().toString();
        userRef.child(tempkey).child("name").setValue(name);
        userRef.child(tempkey).child("birthday").setValue(birthday);
        userRef.child(tempkey).child("firstday").setValue(firstday);
        userRef.child(tempkey).child("gender").setValue(gender);
        PreferenceUtil.setValue(ProfileActivity.this, "autoSignin", "true");
    }


    public void getIntentFromPhoneConnectActivity() {
        Intent intent = getIntent();
        tempkey = intent.getExtras().getString("tempkey2");
    }

    public void init() {
        textViewBirthday = (TextView) findViewById(R.id.textViewBirthday);
        textViewFirstday = (TextView) findViewById(R.id.textViewFirstday);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        name_edit = (EditText) findViewById(R.id.name_edit);
        start_btn = (Button) findViewById(R.id.start_btn);
        radiogender = (RadioGroup) findViewById(R.id.radiogender);
        imageProfile = findViewById(R.id.imageProfile);
    }

    public void imageUpload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select App"),999);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
//            String realPath = RealPathUtil.getRealPath(this, uri);
//            upload(realPath);
            upload(uri);
        }
    }
    private void upload(Uri file) {
        // 파이어베이스의 스토리지 파일node
        String temp[] = file.getPath().split("/"); // 파일이름 꺼내는건 다시
        String filename = temp[temp.length - 1];
        StorageReference riversRef = storageRef.child("files/" + filename);
        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("Storage", "downloadUrl=" + downloadUrl.getPath());
                Glide.with(ProfileActivity.this)                 // 글라이드 초기화
                        .load(downloadUrl).into(imageProfile);
                Log.e("이미지 로딩","잘 들어오나"+downloadUrl);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(ProfileActivity.this
                                , exception.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
