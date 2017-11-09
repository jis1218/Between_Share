package com.project.between.AnniversaryAndCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.between.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvAddHomeAnniversary;
    ConstraintLayout containerNames;
    ConstraintLayout containerViewContents;
    ImageButton btnCommonHome,btnCommonAlbum,btnCommonChatting,btnCommonNotification,btnCommonMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initListener();
        initFragment();
    }
    public void initFragment(){
        Log.e("---------Activity----","  [Add] ListFragment()-----");
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.container, new MainFragment())    // ListFragment.onAttach(this)인 셈
//                .commit();
        Log.e("---------Activity----","  [Commit] ListFragment()-----");

    }
    public void initListener(){
        tvAddHomeAnniversary.setOnClickListener(this);
        containerViewContents.setOnClickListener(this);
        containerNames.setOnClickListener(this);
        btnCommonNotification.setOnClickListener(this);
        btnCommonAlbum.setOnClickListener(this);
        btnCommonHome.setOnClickListener(this);
        btnCommonMore.setOnClickListener(this);
    }

    public void initView(){
        // 등록버튼[+버튼 클릭시], 기념일 클릭시 리스트 이동, 이름들 선택시 리스트 버튼
        tvAddHomeAnniversary = (TextView) findViewById(R.id.tvAddHomeAnniversary);
        containerViewContents= (ConstraintLayout) findViewById(R.id.containerViewContents);
        containerNames       = (ConstraintLayout) findViewById(R.id.containerNames);
        btnCommonHome        = (ImageButton) findViewById(R.id.btnCommonHome);
        btnCommonMore        = (ImageButton) findViewById(R.id.btnCommonMore);
        btnCommonAlbum       = (ImageButton) findViewById(R.id.btnCommonAlbum);
        btnCommonChatting    = (ImageButton) findViewById(R.id.btnCommonChatting);
        btnCommonNotification= (ImageButton) findViewById(R.id.btnCommonNotification);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tvAddHomeAnniversary :
                startActivity(new Intent(HomeActivity.this, AnniversaryListActivity.class));
                Toast.makeText(HomeActivity.this, "기념일 누름",Toast.LENGTH_SHORT).show();
                break;
            case R.id.containerViewContents :
            case R.id.containerNames :
                startActivity(new Intent(HomeActivity.this, CalendarWriteActivity.class));
                Toast.makeText(HomeActivity.this, "add 기념일 누름",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCommonNotification :
                btnCommonAlbum.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonHome.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonMore.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonNotification.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            case R.id.btnCommonAlbum :
                btnCommonNotification.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonHome.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonMore.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonAlbum.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            case R.id.btnCommonHome :
                btnCommonAlbum.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonNotification.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonMore.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonHome.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            case R.id.btnCommonMore :
                btnCommonAlbum.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonHome.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonNotification.setImageResource(android.R.drawable.btn_star_big_off);
                btnCommonMore.setImageResource(android.R.drawable.btn_star_big_on);
                break;
        }
    }
}
