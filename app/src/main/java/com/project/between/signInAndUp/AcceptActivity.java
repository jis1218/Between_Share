package com.project.between.signInAndUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.AnniversaryActivity;
import com.project.between.R;

public class AcceptActivity extends AppCompatActivity {

    Button download_btn;
    EditText myNum_edit;
    EditText friendNum_edit;

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference tempRoomRef;

    String myNumber;
    String friendNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        Intent intent = getIntent();
        myNumber = intent.getStringExtra("myNumber");
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        /*수정
        tempRoomRef = database.getReference("temp").child(myNumber).child("status");
        */
        tempRoomRef = database.getReference("temp");

        initView();

        goToProfileActivity();

//        Intent intent = getIntent();
//        myNumber = intent.getExtras().getString("myPhoneMatch");
//        friendNumber = intent.getExtras().getString("friendPhoneMatch");


    }

    public void goToProfileActivity(){

        tempRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                 /*수정

                     if(snapshot.getValue().equals(myNumber)){
                         Intent intent = new Intent(AcceptActivity.this, ProfileActivity.class);
                         startActivity(intent);
                     }
                 */
                    if(snapshot.hasChild("check")) {
                        if(snapshot.child("check").getValue().equals(myNumber)){
                            Intent intent = new Intent(AcceptActivity.this, ProfileActivity.class);
                            intent.putExtra("tempkey2",myNumber);
                            startActivity(intent);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void downloadLink(View view) {
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("text/plain");
      String text ="https://play.google.com/store/apps/details?id=kr.co.vcnc.android.couple";
      intent.putExtra(Intent.EXTRA_TEXT,text);

      Intent chooser = Intent.createChooser(intent,"친구에게 공유하기");
      startActivity(chooser);

    }

    private void initView() {

        download_btn = findViewById(R.id.download_btn);
        myNum_edit = findViewById(R.id.myNum_edit);
        friendNum_edit = findViewById(R.id.friendNum_edit);

    }
}
