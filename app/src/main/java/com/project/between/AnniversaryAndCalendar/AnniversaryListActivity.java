package com.project.between.AnniversaryAndCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.AnniversaryAndCalendar.vo.AnniversaryListVO;
import com.project.between.R;
import com.project.between.util.AnniversaryUtil;

import java.util.ArrayList;
import java.util.List;

/*
    list : 기념일 누르면 진입
 */
public class AnniversaryListActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference myAnniversaryEventRef;
    AnniversaryListVO anniversaryListVO;
    List<AnniversaryListVO> dataReverseBefore = new ArrayList<>();
    List<AnniversaryListVO> data = new ArrayList<>();
    AnniversaryCustomListAdapter adapter;

    String key          = "";
    String title        = "";
    String date         = "";
    String dday         = "";
    String homeYn       = "";
    String dayCountOneYn= "";

    private LinearLayout addAniversary;
    private ConstraintLayout containerPictures, containerFirst, containerSecond;
    private RecyclerView rvDay, rvCustomDay;
    private TextView tvUpdate, tvTitle, tvDateCount, tvDate, tvCustomDday, tvCustomTitle, tvCustomDate;
    private ImageView pictureYou, ptictureMe;

    String myEmail, otherEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary_list);

        // 내 정보 및 상대방 정보
        myEmail = "test1@gmail.com";
        otherEmail = "test2@gmail.com";

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("기념일");

        initView();
        initOnClickListener();

        database = FirebaseDatabase.getInstance();
        myAnniversaryEventRef = database.getReference("anniversary").child(myEmail.replace(".","_"));

        // 대표 기념일이 없으면 tvTitle 숨기고 tvDateCount값, tvDate 값 변경
//        if(true){
        tvTitle.setVisibility(View.GONE);
        tvDateCount.setText("대표 기념일을 등록하면 다가오는 특별한 날도 놓치지 않게 미리 계산할 수 있어요");
        tvDate.setText("대표 기념일 설정하기");
//        Log.e("디데이 계산---", AnniversaryUtil.getDday("2017년 11월 5일") + "");

    }

    @Override
    protected void onResume() {
        super.onResume();
        onRecyclerViewListener();
    }

    public void onRecyclerViewListener(){
    dataReverseBefore.clear();
    data.clear();
    myAnniversaryEventRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.e("=====날짜 수====",dataSnapshot.getChildrenCount()+"");
            for(DataSnapshot item1 : dataSnapshot.getChildren()){
                date = item1.getKey();
                Log.e("=====1. 키 : 날짜====",item1.getKey().replace(".","_"));
                if(item1.hasChildren()){
                    for(DataSnapshot item2 : item1.getChildren()){
                        key = item2.getKey();
                        for(DataSnapshot item3 : item2.getChildren()) {
                            if(item3.getKey().equals("title")){
                                title = item3.getValue().toString();
                            }
                            if(item3.getKey().equals("homeYn")){
                                homeYn = item3.getValue().toString();
                            }
                            if(item3.getKey().equals("date")){
                                dday = item3.getValue().toString();
                            }
                            if(item3.getKey().equals("dayCountOneYn")){
                                dayCountOneYn = item3.getValue().toString();
                            }
                        }
                        int intDday = AnniversaryUtil.getDday(dday);
                        if(intDday < 0) {
                            if(dayCountOneYn.equals("true")){
                                dday = intDday*(-1) + "일 남음";
                            }else{
                                dday = "D "+intDday;
                            }
                        } else {
                            if(dayCountOneYn.equals("true")){
                                dday = intDday + "일 지남";
                            }else{
                                dday = "D +"+intDday;
                            }
                        }
                        anniversaryListVO = new AnniversaryListVO(key, title, date, dday, homeYn);
                        dataReverseBefore.add(anniversaryListVO);

                    }
                }
            }

            // 리스트 뒤집기
            for(int i=dataReverseBefore.size()-1; i>=0; i--){
                data.add(dataReverseBefore.get(i));
            }

            initRecyclerViewListener();
//        }
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addAniversary:
                startActivity(new Intent(AnniversaryListActivity.this, CalendarWriteActivity.class));
                Toast.makeText(AnniversaryListActivity.this, "추가 누름", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvUpdate:
                startActivity(new Intent(AnniversaryListActivity.this, AnniversarySettingActivity.class));
                Toast.makeText(AnniversaryListActivity.this, "수정", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvDateCount:
                if(tvTitle.getVisibility() == View.GONE){
                    startActivity(new Intent(AnniversaryListActivity.this, AnniversarySettingActivity.class));
                    Toast.makeText(AnniversaryListActivity.this, "설정", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvCustomDate:
                if(tvTitle.getVisibility() == View.GONE){
                    startActivity(new Intent(AnniversaryListActivity.this, AnniversarySettingActivity.class));
                    Toast.makeText(AnniversaryListActivity.this, "설정", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initOnClickListener(){
        addAniversary.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvDateCount.setOnClickListener(this);
    }
    private void initRecyclerViewListener(){
        Log.e("initRecyclerViewLi", data.size()+"");
        adapter = new AnniversaryCustomListAdapter();
        rvCustomDay.setAdapter(adapter);
//        linearManager.setStackFromEnd(true);
        rvCustomDay.setLayoutManager(new LinearLayoutManager(this));
        adapter.dataSetChanged(data);

    }
    private void initView(){
        tvUpdate = (TextView) findViewById(R.id.tvUpdate);
        addAniversary = (LinearLayout) findViewById(R.id.addAniversary);

        containerSecond = (ConstraintLayout) findViewById(R.id.containerSecond);
        rvCustomDay = (RecyclerView) findViewById(R.id.rvCustomDay);
        containerFirst = (ConstraintLayout) findViewById(R.id.containerFirst);
        rvDay = (RecyclerView) findViewById(R.id.rvDay);
        tvDate = (TextView) findViewById(R.id.tvCustomDate);
        tvDateCount = (TextView) findViewById(R.id.tvDateCount);
        tvTitle = (TextView) findViewById(R.id.tvCustomTitle);
        tvCustomTitle = (TextView) findViewById(R.id.tvCustomTitle);
        containerPictures = (ConstraintLayout) findViewById(R.id.containerPictures);
        ptictureMe = (ImageView) findViewById(R.id.ptictureMe);
        pictureYou = (ImageView) findViewById(R.id.pictureYou);
        tvUpdate = (TextView) findViewById(R.id.tvUpdate);

    }
}
