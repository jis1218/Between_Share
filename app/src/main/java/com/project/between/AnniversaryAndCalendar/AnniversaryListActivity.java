package com.project.between.AnniversaryAndCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.between.R;
import com.project.between.util.AnniversaryUtil;

/*
    list : 기념일 누르면 진입
 */
public class AnniversaryListActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference anniversaryRef;
    private TextView tvUpdate;
    private LinearLayout addAniversary;
    private ImageView pictureYou;
    private ImageView ptictureMe;
    private ConstraintLayout containerPictures;
    private TextView tvTitle;
    private TextView tvDateCount;
    private TextView tvDate;
    private RecyclerView rvDay;
    private ConstraintLayout containerFirst;
    private RecyclerView rvCustomDay;
    private ConstraintLayout containerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        myToolbar.setBackgroundColor(Color.parseColor("#80000000"));
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("기념일");


        initView();
        initListener();


        // 대표 기념일이 없으면 tvTitle 숨기고 tvDateCount값, tvDate 값 변경
//        if(true){
            tvTitle.setVisibility(View.GONE);
            tvDateCount.setText("대표 기념일을 등록하면 다가오는 특별한 날도 놓치지 않게 미리 계산할 수 있어요");
            tvDate.setText("대표 기념일 설정하기");
            Log.e("디데이 계산---", AnniversaryUtil.getDday("2017년 11월 5일")+"");
//        }


//        database = FirebaseDatabase.getInstance();
//        anniversaryRef = database.getReference("message");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        tvUpdate = (TextView) findViewById(R.id.tvUpdate);
        addAniversary = (LinearLayout) findViewById(R.id.addAniversary);

        containerSecond = (ConstraintLayout) findViewById(R.id.containerSecond);
        rvCustomDay = (RecyclerView) findViewById(R.id.rvCustomDay);
        containerFirst = (ConstraintLayout) findViewById(R.id.containerFirst);
        rvDay = (RecyclerView) findViewById(R.id.rvDay);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDateCount = (TextView) findViewById(R.id.tvDateCount);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        containerPictures = (ConstraintLayout) findViewById(R.id.containerPictures);
        ptictureMe = (ImageView) findViewById(R.id.ptictureMe);
        pictureYou = (ImageView) findViewById(R.id.pictureYou);
        tvUpdate = (TextView) findViewById(R.id.tvUpdate);

    }
    private void initListener(){
        addAniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnniversaryListActivity.this, CalendarWriteActivity.class));
                Toast.makeText(AnniversaryListActivity.this, "추가 누름", Toast.LENGTH_SHORT).show();
            }
        });
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnniversaryListActivity.this, AnniversarySettingActivity.class));
                Toast.makeText(AnniversaryListActivity.this, "수정", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
