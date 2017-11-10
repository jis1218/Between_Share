package com.project.between.AnniversaryAndCalendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.project.between.R;
import com.project.between.chatting.ChattingActivity;
import com.project.between.more.MoreActivity;

import java.io.IOException;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvAddHomeAnniversary;
    ConstraintLayout containerNames;
    ConstraintLayout containerViewContents;
    ImageButton btnCommonHome;
    ImageButton btnCommonChatting;
    ImageButton btnCommonMore;
    ImageView ivBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initListener();
        initBackground();
//        initFragment();
    }

    private void initBackground() {
        ivBackground=(ImageView)findViewById(R.id.imageView);
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
        btnCommonHome.setOnClickListener(this);
        btnCommonMore.setOnClickListener(this);
        btnCommonChatting.setOnClickListener(this);
    }

    public void initView(){
        // 등록버튼[+버튼 클릭시], 기념일 클릭시 리스트 이동, 이름들 선택시 리스트 버튼
        tvAddHomeAnniversary = (TextView) findViewById(R.id.tvAddHomeAnniversary);
        containerViewContents= (ConstraintLayout) findViewById(R.id.containerViewContents);
        containerNames       = (ConstraintLayout) findViewById(R.id.containerNames);
        btnCommonHome        = (ImageButton) findViewById(R.id.btnCommonHome);
        btnCommonMore        = (ImageButton) findViewById(R.id.btnCommonMore);
        btnCommonChatting    = (ImageButton) findViewById(R.id.btnCommonChatting);
    }

    public static final int SELECT_IMAGE=1003;
    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch(id){
            case R.id.tvAddHomeAnniversary :
                startActivity(new Intent(HomeActivity.this, AnniversaryListActivity.class));
                Toast.makeText(HomeActivity.this, "기념일 누름",Toast.LENGTH_SHORT).show();
                break;
            case R.id.containerViewContents :
            case R.id.containerNames :
                startActivity(new Intent(HomeActivity.this, CalendarWriteActivity.class));
                Toast.makeText(HomeActivity.this, "add 기념일 누름",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCommonHome :
                btnCommonHome.setImageResource(R.drawable.home_on);
                btnCommonMore.setImageResource(R.drawable.my_off);
                break;
            case R.id.btnCommonMore :
//                btnCommonHome.setImageResource(R.drawable.home_off);
//                btnCommonMore.setImageResource(R.drawable.my_on);
                startActivity(new Intent(HomeActivity.this, MoreActivity.class));
                break;
            case R.id.btnCommonChatting:
                startActivity(new Intent(HomeActivity.this, ChattingActivity.class));
                break;
            case R.id.imageView:
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_IMAGE);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ivBackground.setImageBitmap(bitmap);

                        saveDB();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveDB() {

    }
}
