package com.project.between.AnniversaryAndCalendar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class AnniversarySettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView tvAnnivarsarySettingTitle,
                     tvAnnivarsarySettingDateCheck,
                     tvAnnivarsarySettingDate,
                     tvCheckHomeCondition;
    private ImageView btnGarbage, checkViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary_setting);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        myToolbar.setBackgroundColor(Color.parseColor("#80000000"));
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("설정");

        initView();
        initListener();
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
//        @android:drawable/checkbox_off_background
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        checkViewHome = (ImageView) findViewById(R.id.checkViewHomeOn);
        btnGarbage = (ImageView) findViewById(R.id.btnGarbage);
        tvAnnivarsarySettingDateCheck = (TextView) findViewById(R.id.tvAnnivarsarySettingDateCheck);
        tvAnnivarsarySettingTitle = (TextView) findViewById(R.id.tvAnnivarsarySettingTitle);
        tvAnnivarsarySettingDate = (TextView) findViewById(R.id.tvAnnivarsarySettingDate);
        tvCheckHomeCondition = (TextView) findViewById(R.id.tvCheckHomeCondition); // 이미지뷰가 on상태인지 off상태인지 확인
    }
    private void initListener(){
        // holder에서 적용해야됨
        /*checkViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkCondition = tvCheckHomeCondition.getText().toString();
                if(checkCondition.equals("yes")) {
                    tvCheckHomeCondition.setText("off");
                    checkViewHome.setImageResource(android.R.drawable.checkbox_off_background);
                } else {
                    // db에서 누른 기념일은 on으로 변경하고 다른 모든 기념일 off 저장
                    tvCheckHomeCondition.setText("on");
                    checkViewHome.setImageResource(android.R.drawable.checkbox_on_background);
                }
            }
        });*/
    }
}
