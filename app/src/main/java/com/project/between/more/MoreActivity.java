package com.project.between.more;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.R;
import com.project.between.signInAndUp.SignUpActivity;
import com.project.between.signInAndUp.User;
import com.project.between.util.PreferenceUtil;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int SELECT_IMAGE = 1101;
    private ImageView ivSumNail;
    private EditText etName;
    private EditText etBirthday;
    private EditText etGender;
    private EditText etPhoneNumber;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();
        initInfo();
    }

    private void initInfo() {
        database = FirebaseDatabase.getInstance();
//        final String myEmail = "plemonple@naver_com";
//        Log.e("userRef",userRef.toString()+"");
//        Log.e("=====Email",myEmail+"");Log.e("==========",""+myEmail);
        userRef = database.getReference("user");

        if(userRef!=null) {


            String useremail=PreferenceUtil.getStringValue(this,"userEmail");

            Log.e("=======","========================================");
            Log.e("useremail",useremail);
            Log.e("=======","========================================");
            final DatabaseReference myInfo = userRef.child(useremail.replace(".","_"));

            ValueEventListener placeListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name=dataSnapshot.child("name").getValue()+"";
                    String birthday=dataSnapshot.child("birthday").getValue()+"";
                    String gender=dataSnapshot.child("gender").getValue()+"";
                    String phone=dataSnapshot.child("myPhone").getValue()+"";

                    etName.setText(name);
                    etBirthday.setText(birthday);
                    etGender.setText(gender);
                    etPhoneNumber.setText(phone);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "취소 됨", Toast.LENGTH_SHORT).show();
                }
            };
            myInfo.addListenerForSingleValueEvent(placeListener);
            //TODO: 비트맵 파일로 변환해서 이미지 뷰에 넣을것
            //photoRef = database.getReference("photo").child(photoRoom).child("profile").child(myNum);
        }
    }

    public void initView(){
        ivSumNail=(ImageView)findViewById(R.id.ivSumNail);
        etName=(EditText)findViewById(R.id.etName);
        etBirthday=(EditText)findViewById(R.id.etBirtyday);
        etGender=(EditText)findViewById(R.id.etGender);
        etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.ivSumNail:
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
                        ivSumNail.setImageBitmap(bitmap);

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
