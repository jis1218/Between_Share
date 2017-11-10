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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.AnniversaryAndCalendar.vo.AlarmReceiveUser;
import com.project.between.AnniversaryAndCalendar.vo.Anniversary;
import com.project.between.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
    write : 등록하기 버튼 클릭시
 */
public class CalendarWriteActivity extends AppCompatActivity implements View.OnClickListener {
//    String command = "I";
    // 내 정보 및 상대방 정보
    String myEmail, otherEmail;

    FirebaseDatabase database;
    DatabaseReference anniversaryRef;

    ImageView btnSave;
    Button btnCalendarTypeChoice, btnCalender, btnRepeat;

    EditText etTitle, etMemo;

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

    Switch switchHome, switchDayCount;
    ConstraintLayout containerNotificationsYN;
    CheckBox meCheck, otherCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_write);

        database = FirebaseDatabase.getInstance();
        anniversaryRef = database.getReference("anniversary");
//
//        if(getIntent() != null) {
//            Intent intent = getIntent();
//            command = intent.getStringExtra("command");
//        }

        initView();
        initListener();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("캘린더");
    }
    private void save(){
        String title            = etTitle.getText().toString();
        String anniType         = btnCalendarTypeChoice.getText().toString();
        String date             = tvCalenderDate.getText().toString();
        String repeatYN         = tvRepeatYN.getText().toString();
        String choiceAlram      = tvChoiceAlram.getText().toString();
        String alarmChoiceUser  = tvChoiceAlram.getText().toString();
        String memo             = etMemo.getText().toString();
        String myAlarm          = "";
        String otherAlarm       = "";

        myAlarm = myEmail;
        otherAlarm = otherEmail;
        AlarmReceiveUser alarmReceiveUser = null;
        Anniversary anniversary = new Anniversary();

        if(title.equals("")){
            title = "기념일";
        }
        if(anniType.equals("유형 선택")) {
            anniType = "기타";
        }

        // 날짜에서 숫자 뽑기 : 키 생성용
        String strDate = date.split("일")[0];
        strDate = strDate.replace("년 ","");
        strDate = strDate.replace("월 ","");

        // 날짜
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

        anniversary.setTitle(title);
        anniversary.setAnniType(anniType);
        anniversary.setChoiceAlram(choiceAlram);
        anniversary.setDate(date);
        anniversary.setDayCountOneYn(dayCountOneYn+"");
        anniversary.setHomeYn(homeYn+"");
        anniversary.setReg_date(dateFormat.format(new Date()));
        anniversary.setReg_time(timeFormat.format(new Date()));
        anniversary.setRepeatYN(repeatYN);
        anniversary.setMemo(memo);

        // 알림 수신자 저장
        if(meCheck.isChecked() && otherCheck.isChecked()){
            alarmReceiveUser = new AlarmReceiveUser(myAlarm, otherAlarm);
        } else {
            if (otherCheck.isChecked()){
                alarmReceiveUser = new AlarmReceiveUser(otherAlarm);
            } else if(meCheck.isChecked()){
                alarmReceiveUser = new AlarmReceiveUser(myAlarm);
            }
        }
        if(alarmReceiveUser != null){
            anniversary.setAlarmReceiveUser(alarmReceiveUser);
        }

        if(homeYn){
            Map<String, Object> hopperUpdates = new HashMap<String, Object>();
            hopperUpdates.put("homeYn", "false");
            Log.e("----", "여기");
            DatabaseReference mEmailRef = anniversaryRef.child(myAlarm.replace(".","_"));
//            changeHomeFalse(mEmailRef);
            // 키 에 값 저장
//            anniversaryRef.child(otherAlarm.replace(".","_")).updateChildren(hopperUpdates);
        }
//          디비 등록
        anniversaryRef.child(myAlarm.replace(".","_")).child(strDate).child(dateFormat.format(new Date())+timeFormat.format(new Date())).setValue(anniversary);
        anniversaryRef.child(otherAlarm.replace(".","_")).child(strDate).child(dateFormat.format(new Date())+timeFormat.format(new Date())).setValue(anniversary);
//            Log.e("-----save data-----",anniversaryRef.push().getKey()+" , "+strDate+", myAlarm : "+myAlarm+", otherAlarm : "+otherAlarm+", title : "+ title +", anniType : "+ anniType +", date : "+ date +", repeatYN : "+ repeatYN +", choiceAlram : "+ choiceAlram +", memo : "+ memo +", homeYn : "+ homeYn +", dayCountOneYn : "+ dayCountOneYn);

        // 종료
        finish();
    }

    // 이메일 테이블 넘어옴
    private void changeHomeFalse(DatabaseReference databaseRef){

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("=====Write 날짜 수====",dataSnapshot.getChildrenCount()+"");
                // 기념일
                for(DataSnapshot item1 : dataSnapshot.getChildren()){
                    for(DataSnapshot item2 : item1.getChildren()){
                        Log.e("=====Write 글 수====",item1.getChildrenCount()+"");

                        // 글 키 : item2.getKey()
                        for(DataSnapshot item3 : item2.getChildren()){
                            // 컬럼 데이터 : item3.getKey()
                            if(item3.getKey().equals("homeYn")) {
                                Map<String, Object> data = new HashMap<>();
                                data.put("homeYn","false");
                                DatabaseReference tempRef = anniversaryRef.child(myEmail.replace(".","_")).child(item2.getKey());
                                tempRef.updateChildren(data);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
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
                containerAlarm.setVisibility(View.GONE);
                break;
            // 없음
            case R.id.btnAlarm1 :
                containerNotificationsYN.setVisibility(View.GONE);
                tvChoiceAlram.setText("없음");

                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[0].setImageResource(android.R.drawable.checkbox_on_background);

                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm2 :
                containerNotificationsYN.setVisibility(View.VISIBLE);
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[1].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[1].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm3 :
                containerNotificationsYN.setVisibility(View.VISIBLE);
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[2].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[2].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm4 :
                containerNotificationsYN.setVisibility(View.VISIBLE);
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[3].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[3].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm5 :
                containerNotificationsYN.setVisibility(View.VISIBLE);
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[4].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[4].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm6 :
                containerNotificationsYN.setVisibility(View.VISIBLE);
                for(int i=0; i<arrCheckAlarm.length; i++){
                    arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
                }
                arrCheckAlarm[5].setImageResource(android.R.drawable.checkbox_on_background);

                // 텍스트 출력
                tvChoiceAlram.setText(arrBtnAlarm[5].getText().toString());
                containerAlarm.setVisibility(View.GONE);
                break;
            case R.id.btnAlarm7 :
                containerNotificationsYN.setVisibility(View.VISIBLE);
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
            case R.id.btnSave :
                save();
                break;
        }
    }
    private void initListener(){
        // 저장 버튼
        btnSave.setOnClickListener(this);
        // 날짜 선택 버튼
        btnCalendarTypeChoice.setOnClickListener(this);
        btnCalender.setOnClickListener(this);

        // 알림 선택 버튼
        btnCheckAlarmOn.setOnClickListener(this);
        containerAlarm.setOnClickListener(this);

        // 기념일 유형 선택 버튼
        containerAnniversaryTypeChoice.setOnClickListener(this);
        for(int i=0; i<arrBtnAnniType.length; i++){
            arrBtnAnniType[i].setOnClickListener(this);
        }

        // 반복 버튼
        btnRepeat.setOnClickListener(this);
        for(int i=0; i<arrBtnAlarm.length; i++){
            arrBtnAlarm[i].setOnClickListener(this);
        }

        // 반복 리스트 버튼
        for(int i=0; i<arrBtnRepeat.length; i++){
            arrBtnRepeat[i].setOnClickListener(this);
        }

        // 홈화면 표시 스위치
        switchHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("홈버튼 체크 상태 : ", b+"");
                homeYn = b;
            }
        });

        // 설정일을 1일로 세기 스위치
        switchDayCount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("설정일 버튼 체크 상태 : ", b+"");
                dayCountOneYn = b;
            }
        });
    }
    private boolean homeYn = false;
    private boolean dayCountOneYn = true;
    private void initView(){
        // 내 정보 및 상대방 정보
        myEmail = "test1@gmail.com";
        otherEmail = "test2@gmail.com";

        etTitle = (EditText) findViewById(R.id.etTitle);
        etMemo = (EditText) findViewById(R.id.etMemo);

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

        meCheck = (CheckBox) findViewById(R.id.meCheck);
        otherCheck = (CheckBox) findViewById(R.id.otherCheck);

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

        // 홈화면 표시 스위치
        switchHome = (Switch) findViewById(R.id.switchHome);

        // 설정일을 1일로 세기 스위치
        switchDayCount = (Switch) findViewById(R.id.switchDayCount);

        containerNotificationsYN = (ConstraintLayout) findViewById(R.id.containerNotificationsYN);

        initAlarm();
        initAnniversary();
    }

    /*
        alarm 초기화
        1. 기념일 등록으로 들어온 경우 : 0
        2. 그외의 경우 : 값 출력
    */
    private void initAlarm(){
        for(int i=0; i<arrCheckAlarm.length; i++){
            arrCheckAlarm[i].setImageResource(android.R.drawable.menuitem_background);
        }
        arrCheckAlarm[0].setImageResource(android.R.drawable.checkbox_on_background);
        containerAlarm.setVisibility(View.GONE);
        containerNotificationsYN.setVisibility(View.GONE);
        tvChoiceAlram.setText("없음");
    }

    /*
        기념일 초기화
        1. 기념일 등록으로 들어온 경우 : 기타
        2. 그외의 경우 : 값 출력
    */
    private void initAnniversary(){
        btnCalendarTypeChoice.setText("기타");
    }
}