package com.project.between.AnniversaryAndCalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 * view : 리스트 내부에서 기념일 누르면 진입
 */
public class CalendarViewActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference anniversaryRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        database = FirebaseDatabase.getInstance();
        anniversaryRef = database.getReference("message");
    }
}
