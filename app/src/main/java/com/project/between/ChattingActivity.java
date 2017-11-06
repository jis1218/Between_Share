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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.Util.TimeConverter;
import com.project.between.domain.MyMessage;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference roomRef;
    Toolbar toolbarChattingActivity;
    RecyclerView recyclerViewChatting;
    EditText editTextPutMessage;
    Button buttonSendMessage;
    String roomkey;
    RecyclerAdapter adapter;
    MyMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        initView();
        setInstanceOfDatabase();
        //setDatabase();
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
    }


    private void getMessageFromDatabase() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MyMessage> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyMessage message = snapshot.getValue(MyMessage.class);
                    //Log.d("======", "what?" + chat);
                    list.add(message);
                }
                adapter.refreshData(list);
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
        message.phone_number = "010010010";
        message.messageTime = TimeConverter.timeConverter(System.currentTimeMillis());
        editTextPutMessage.setText("");
        String time = String.valueOf(System.currentTimeMillis());
        roomRef.child(time).setValue(message);
    }

    private void setRecyclerListener() {
        adapter = new RecyclerAdapter();
        recyclerViewChatting.setAdapter(adapter);
        recyclerViewChatting.setLayoutManager(new LinearLayoutManager(this));
    }
}
