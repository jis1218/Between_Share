package com.project.between;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.domain.User;
import com.project.between.util.PreferenceUtil;
import com.project.between.util.TimeConverter;
import com.project.between.domain.MyMessage;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference roomRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Toolbar toolbarChattingActivity;
    RecyclerView recyclerViewChatting;
    EditText editTextPutMessage;
    Button buttonSendMessage;
    RecyclerAdapter adapter;
    MyMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        initView();
        setInstanceOfDatabase();
        setRecyclerListener();
        getMessageFromDatabase();
        setSupportActionBar(toolbarChattingActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void initView() {
        toolbarChattingActivity = (Toolbar) findViewById(R.id.toolbarChattingActivity);
        recyclerViewChatting = (RecyclerView) findViewById(R.id.recyclerViewChatting);
        editTextPutMessage = (EditText) findViewById(R.id.editTextPutMessage);
        buttonSendMessage = (Button) findViewById(R.id.buttonSendMessage);
    }

    private void setInstanceOfDatabase() {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        roomRef = database.getReference("room").child("happy");
        message = new MyMessage();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Log.d("user_id", user.getUid());
    }

    private void getMessageFromDatabase() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MyMessage> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyMessage message = snapshot.getValue(MyMessage.class);
                    list.add(message);
                }
                adapter.refreshData(list);
                recyclerViewChatting.scrollToPosition(list.size()-1); //chat이 추가될 때마다 RecyclerView의 스크롤을 밑으로 내려줌
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //누르면 메세지 보내는 버튼 리스너 함수
    public void sendMessage(View view) {
        Toast.makeText(this, "하하하", Toast.LENGTH_SHORT).show();
        message.message = editTextPutMessage.getText().toString();
        message.user_id = PreferenceUtil.getStringValue(this, "user_id");
        message.messageTime = TimeConverter.timeConverterMinute(System.currentTimeMillis());
        message.messageDate = TimeConverter.timeConverterDate(System.currentTimeMillis());
        editTextPutMessage.setText("");
        String time = String.valueOf(System.currentTimeMillis());
        roomRef.child(time).setValue(message);

    }

    private void setRecyclerListener() {
        adapter = new RecyclerAdapter(this);
        recyclerViewChatting.setAdapter(adapter);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setStackFromEnd(true); //가장 최신 메시지부터 보여줌
        recyclerViewChatting.setLayoutManager(linearManager);
    }

}
