package com.project.between.signInAndUp;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.util.PreferenceUtil;
import com.project.between.R;

import org.w3c.dom.Text;

public class PhoneConnectActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference tempRoomRef;
    DatabaseReference chatRoomRef;
    DatabaseReference veriRef;
    private EditText myNum_edit;
    private EditText friendNum_edit;
    private Button phone_Connect_btn;
    String tempkey;
    ImageButton phoneNum_img_btn;
    TextView friendPhone_text;
    String phonenumber_local;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_connect);
        initView();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        tempRoomRef = database.getReference("temp");
        chatRoomRef = database.getReference("chatRoom");

        Intent intent = getIntent();
        tempkey = intent.getExtras().getString("tempKey");
        loadPhoneNumberLocalDatabase();
    }

    private void initView() {
        myNum_edit = (EditText) findViewById(R.id.myNum_edit);
        friendNum_edit = (EditText) findViewById(R.id.friendNum_edit);
        phone_Connect_btn = (Button) findViewById(R.id.phone_Connect_btn);
        phoneNum_img_btn = (ImageButton) findViewById(R.id.phoneNum_img_btn);
        friendPhone_text = (TextView) findViewById(R.id.friendPhone_text);
    }

    public void onConnect(View view){

        searchTemp();

    }

    private void searchTemp(){
        final String myNumber = myNum_edit.getText().toString();
        final String friendNumber = friendNum_edit.getText().toString();
        userRef.child(tempkey).child("myPhone").setValue(myNumber);
        userRef.child(tempkey).child("friendNumber").setValue(friendNumber);

        tempRoomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(friendNumber)){
                    Toast.makeText(PhoneConnectActivity.this, "가진 사용자 없음", Toast.LENGTH_LONG).show();
                    dataSnapshot.child(myNumber).child("friendNumber").getRef().setValue(friendNumber);
                    dataSnapshot.child(myNumber).child("status").child("confirm").getRef().setValue("none");
                    String chatRoom = myNumber + friendNumber + "chat";

                    userRef.child(tempkey).child("roomID").child("id").setValue(chatRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "chatroom", chatRoom);

                    moveToProfileActivity();
                    //moveToAcceptActivity(myNumber, friendNumber);

                }else{
                    Toast.makeText(PhoneConnectActivity.this, "가진 사용자 있음", Toast.LENGTH_LONG).show();
                    dataSnapshot.child(friendNumber).child("status").child("confirm").getRef().setValue("yes");
                    String chatRoom = friendNumber+ myNumber + "chat";
                    userRef.child(tempkey).child("roomID").child("id").setValue(friendNumber+ myNumber + "chat");
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "chatroom", chatRoom);
                    moveToProfileActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_phone_connect);
//
//        database = FirebaseDatabase.getInstance();
//        userRef = database.getReference("user");
//        database.getReference().
//
//
//
//        Intent intent = getIntent();
//        tempkey = intent.getExtras().getString("tempKey");
//        initView();
//        loadPhoneNumberLocalDatabase();
//
//    }
//
//
//    public void onConnect(View view) {
//
//        final String myNumber = myNum_edit.getText().toString();
//        final String friendNumber = friendNum_edit.getText().toString();
//        userRef.child(tempkey).child("myPhone").setValue(myNumber);
//        userRef.child(tempkey).child("friendPhone").setValue(friendNumber);
//        tempRoomRef = database.getReference("temp");
//
//        tempRoomRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                // 방이 한 개도 없는 경우
//                if (dataSnapshot.getChildrenCount() == 0) {
//                    tempRoomRef.child(myNumber).child("check").setValue(friendNumber);
//
//                    moveToAcceptActivity(myNumber, friendNumber);
//                }
//
//
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Log.e("확인1", "키 값 확인");
//                    // 내가 요청하는 친구가 이미 방을 만들었을 경우
//                    Log.e("키 값", data.getKey());
//
//                    if (data.getKey().equals(friendNumber)) {
//
//                        Log.e("확인1", "내가 요청하는 친구가 이미 방을 만들었을 경우");
//                        // 그 친구가 요청한 friendPhone이 자신일 경우는 채팅방으로 넘어감
//                        if (data.child("check").getValue(String.class).equals(myNumber)) {
//                            tempRoomRef.child(friendNumber).setValue(myNumber);
//                            Log.e("확인1", "그 친구가 나임");
//                            // 넘어가기 전에 둘이서 채팅할 데이터베이스를 만들고
//
//                            // 넘어감
//                            moveToProfileActivity();
//                            break;
//                            // 그 친구가 요청은 했는데 나는 아님
//                        } else {
//                            Log.e("확인1", "그 친구가 내가 아님");
//                            // 아래와 같이 방을 만들고 기다리게 함
//                            tempRoomRef.child(myNumber).child("check").setValue(friendNumber);
//                            moveToAcceptActivity(myNumber, friendNumber);
//                            break;
//                        }
//                        // 아직 친구가 방을 만들지 않음
//                    } else {
//                        Log.e("확인1", friendNumber + "로 아무도 방을 만들지 않음");
//                        tempRoomRef.child(myNumber).child("check").setValue(friendNumber);
//
//                        /*수정
//                        moveToAcceptActivity(myNumber);
//                        */
//                        moveToAcceptActivity(myNumber, friendNumber);
//                        break;
//                    }
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
    public void moveToProfileActivity() {
        Intent intent = new Intent(PhoneConnectActivity.this, ProfileActivity.class);
        intent.putExtra("tempkey2", tempkey);
        startActivity(intent);
    }
//
    public void moveToAcceptActivity(String myNumber, String friendNumber) {
        Intent intent = new Intent(PhoneConnectActivity.this, AcceptActivity.class);
        intent.putExtra("myNumber", myNumber);
        intent.putExtra("friendNumber", friendNumber);
        intent.putExtra("tempkey2", tempkey);
        startActivity(intent);
    }
//
//

//
    public void loadPhoneNumberLocalDatabase() {

        phoneNum_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                friendNum_edit.setText(phonenumber_local);
                startActivityForResult(intent, 1);
            }
        });
    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();

            phonenumber_local = cursor.getString(0);

            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
