package com.project.between.AnniversaryAndCalendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.between.R;

import java.util.Calendar;

/*
    write : 등록하기 버튼 클릭시
 */
public class CalendarWriteActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference anniversaryRef;
    ImageView btnSave;
    Button btnCalendarTypeChoice, btnCalender, btnRepeat;

    ConstraintLayout containerAnniversaryTypeChoice;
    Button btnAnniType1,btnAnniType2,btnAnniType3,btnAnniType4,btnAnniType5,btnAnniType6,btnAnniType7,btnAnniType8;
    Button[] arrBtnAnniType = { btnAnniType1,btnAnniType2,btnAnniType3,btnAnniType4,btnAnniType5,btnAnniType6,btnAnniType7,btnAnniType8 };
    int[] rIdBtnAnniType = {R.id.btnAnniType1, R.id.btnAnniType2, R.id.btnAnniType3, R.id.btnAnniType4, R.id.btnAnniType5, R.id.btnAnniType6, R.id.btnAnniType7, R.id.btnAnniType8};

    ConstraintLayout containerAlarm;
    Button btnCheckAlarmOn, btnAlarm1, btnAlarm2, btnAlarm3,btnAlarm4, btnAlarm5, btnAlarm6, btnAlarm7;
    Button[] arrBtnAlarm = { btnAlarm1, btnAlarm2, btnAlarm3, btnAlarm4, btnAlarm5, btnAlarm6, btnAlarm7 };
    int[] rIdBtnAlarm = {R.id.btnAlarm1, R.id.btnAlarm2, R.id.btnAlarm3, R.id.btnAlarm4, R.id.btnAlarm5, R.id.btnAlarm6, R.id.btnAlarm7};

    ImageView checkAlarm1, checkAlarm2, checkAlarm3, checkAlarm4, checkAlarm5, checkAlarm6, checkAlarm7;
    ImageView[] arrCheckAlarm = { checkAlarm1, checkAlarm2, checkAlarm3, checkAlarm4, checkAlarm5, checkAlarm6, checkAlarm7 };
    int[] rIdCheckAlarm = {R.id.checkAlarm1, R.id.checkAlarm2, R.id.checkAlarm3, R.id.checkAlarm4, R.id.checkAlarm5, R.id.checkAlarm6, R.id.checkAlarm7};

    ConstraintLayout containerRepeat;
    Button btnRepeatNo, btnRepeatYes;
    Button[] arrBtnRepeat = { btnRepeatNo, btnRepeatYes };
    int[] rIdBtnRepeat = {R.id.btnRepeatNo, R.id.btnRepeatYes};

    ImageView containerRepeatNo, containerRepeatYes;
    ImageView[] arrCheckRepeat = { containerRepeatNo, containerRepeatYes };
    int[] rIdCheckRepeat = {R.id.containerRepeatNo, R.id.containerRepeatYes};

    TextView tvCalenderDate, tvChoiceAlram, tvRepeatYN;
    Calendar calendar;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_write);

        database = FirebaseDatabase.getInstance();
        anniversaryRef = database.getReference("message");

        initView();
        initListener();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("캘린더");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initView(){
        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnCalendarTypeChoice = (Button) findViewById(R.id.btnCalendarTypeChoice);
        containerAnniversaryTypeChoice = (ConstraintLayout) findViewById(R.id.containerAnniversaryTypeChoice);
        btnCalender = (Button) findViewById(R.id.btnCalender);
        tvCalenderDate = (TextView) findViewById(R.id.tvCalenderDate);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        btnCheckAlarmOn = (Button) findViewById(R.id.btnCheckAlarmOn);
        containerAlarm  = (ConstraintLayout) findViewById(R.id.containerAlarm);
        tvChoiceAlram = (TextView) findViewById(R.id.tvChoiceAlram);

        for(int i=0; i<arrBtnAnniType.length; i++){
            arrBtnAnniType[i] = (Button) findViewById(rIdBtnAnniType[i]);
        }
        for(int i=0; i<arrCheckAlarm.length; i++){
            arrCheckAlarm[i] = (ImageView) findViewById(rIdCheckAlarm[i]);
        }
        for(int i=0; i<arrBtnAlarm.length; i++){
            arrBtnAlarm[i] = (Button) findViewById(rIdBtnAlarm[i]);
        }
        for(int i=0; i<arrBtnRepeat.length; i++){
            arrBtnRepeat[i] = (Button) findViewById(rIdBtnRepeat[i]);
        }
        for(int i=0; i<arrCheckRepeat.length; i++){
            arrCheckRepeat[i] = (ImageView) findViewById(rIdCheckRepeat[i]);
        }

        containerRepeat = (ConstraintLayout) findViewById(R.id.containerRepeat);
        tvRepeatYN = (TextView) findViewById(R.id.tvRepeatYN);
        btnRepeat = (Button) findViewById(R.id.btnRepeat);

        // alarm 초기화
        // 1. 기념일 등록으로 들어온 경우 : 0
        // 2. 그외의 경우 : 값 출력
        for(int i=0; i<arrCheckAlarm.length; i++){
            arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
        }
        arrCheckAlarm[0].setImageResource(android.R.drawable.checkbox_on_background);
        containerAlarm.setVisibility(View.GONE);
        tvChoiceAlram.setText("없음");
    }
    private void initListener(){
        btnCalendarTypeChoice.setOnClickListener(this);
        containerAnniversaryTypeChoice.setOnClickListener(this);
        btnCalender.setOnClickListener(this);
        btnCheckAlarmOn.setOnClickListener(this);
        containerAlarm.setOnClickListener(this);
        btnRepeat.setOnClickListener(this);
        for(int i=0; i<arrBtnAlarm.length; i++){
            arrBtnAlarm[i].setOnClickListener(this);
        }
        for(int i=0; i<arrBtnAnniType.length; i++){
            arrBtnAnniType[i].setOnClickListener(this);
        }
        for(int i=0; i<arrBtnRepeat.length; i++){
            arrBtnRepeat[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnCalendarTypeChoice:
                if(containerAnniversaryTypeChoice.getVisibility() == View.VISIBLE)
                    containerAnniversaryTypeChoice.setVisibility(View.GONE);
                else
                    containerAnniversaryTypeChoice.setVisibility(View.VISIBLE);
                break;
            case R.id.containerAnniversaryTypeChoice:
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnCalender:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        tvCalenderDate.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                    }
                }, year, month, day).show();
                break;
            case R.id.btnCheckAlarmOn :
                containerAlarm.setVisibility(View.VISIBLE);
                break;
            case R.id.containerAlarm:
            case R.id.btnAlarm1 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[0].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(btnAlarm1.getText().toString());

                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm2 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[1].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[1].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm3 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[2].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[2].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm4 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[3].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[3].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm5 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[4].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[4].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm6 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[5].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[5].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm7 :
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[6].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[6].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType1 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[0].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                Log.e("----","=======");
                break;
            case R.id.btnAnniType2 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[1].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType3 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[2].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType4 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[3].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType5 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[4].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType6 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[5].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType7 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[6].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnAnniType8 :
                // 텍스트 출력
                btnCalendarTypeChoice.setText(arrBtnAnniType[7].getText().toString());
                containerAnniversaryTypeChoice.setVisibility(View.GONE);
                break;
            case R.id.btnRepeatNo :
                // 텍스트 출력
                tvRepeatYN.setText(arrBtnRepeat[0].getText().toString());
                for(int i=0; i<arrCheckRepeat.length; i++){
                    arrCheckRepeat[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckRepeat[0].setImageResource(android.R.drawable.checkbox_on_background);
                containerRepeat.setVisibility(View.GONE);
                break;
            case R.id.btnRepeatYes :
                // 텍스트 출력
                tvRepeatYN.setText(arrBtnRepeat[1].getText().toString());
                for(int i=0; i<arrCheckRepeat.length; i++){
                    arrCheckRepeat[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckRepeat[1].setImageResource(android.R.drawable.checkbox_on_background);
                containerRepeat.setVisibility(View.GONE);
                break;
            case R.id.btnRepeat :
                containerRepeat.setVisibility(View.VISIBLE);
                break;
        }
    }
}