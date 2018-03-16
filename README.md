# Between

## Between 소개

- #### 안드로이드 스튜디오를 이용해 커플 채팅 앱인 Between의 기능을 따라 구현
- #### 기간 : 2017년 11월 10일 ~ 11일

## 프로젝트 목적

- #### Google Firebase를 이용해 이메일 로그인, Realtime Database(NoSQL)의 기능을 사용하여 앱 구현

## 스크린샷
![pic1](https://github.com/jis1218/Between_Share/blob/master/img/pic1.png)<br>
![pic2](https://github.com/jis1218/Between_Share/blob/master/img/pic2.png)<br>
![pic3](https://github.com/jis1218/Between_Share/blob/master/img/pic3.png)<br>

## 담당한 부분
### by Insup Jung

#### 1. 회원가입 기능

![pic4](https://github.com/jis1218/Between_Share/blob/master/img/pic4.png)<br>
- Firebase의 FirebaseAuth 클래스를 이용하여 이메일과 비밀번호를 입력받았다.
```java
public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    /**
     * 코드 생략
    **/
    public void signup(View view) {
        final String email = signUp_email_edit.getText().toString();
        final String password = signUp_password_edit.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "확인", Toast.LENGTH_SHORT).show();
                            FirebaseUser fUser = auth.getCurrentUser();
                            moveToNext();
                            PreferenceUtil.setValue(SignUpActivity.this, "userEmail", email);
                            PreferenceUtil.setValue(SignUpActivity.this, "password", password);
                            tempKey = email.replace(".", "_"); //firebase에서 "."이 사용이 안됨
                            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            User user = new User(fUser.getUid(), email, refreshedToken);
                            userRef.child(tempKey).setValue(user);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "확인 안됨 : " + e.toString(), Toast.LENGTH_SHORT).show();
                Log.e("로그", e.toString());

            }
        });
    }
```
- #### 이메일, 비밀번호 유효성 검사 - 정규식(regular expression)을 이용하였다.
```java
    public static boolean isValidEmail(String email) {
        String regex = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isValidPassword(String password) {
        // 영문자와 숫자만 허용
        String regex = "^[0-9A-Za-z]{8,14}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }
```

#### 2. 내 번호와 상대 번호 일치하는지 인증
- #### 스크린샷으로 본 간단한 흐름
![pic5](https://github.com/jis1218/Between_Share/blob/master/img/pic5.png)<br>

- #### Firebase DB의 데이터 모습
![pic6](https://github.com/jis1218/Between_Share/blob/master/img/pic6.png)<br>

- #### 내가 나의 번호와 상대 번호를 입력하고 연결하기를 누르게 되면  addListenerForSingleValueEvent 메서드를 통해 서버의 temp 노드 아래에 상대방의 번호가 있는지 확인한다. 없다면 나의 번호를 이용해 노드를 추가하고 위의 그림과 같이 friendNumber와 status 노드를 추가하고 내용을 저장한다. status 밑에 confirm의 값으로는 "none"이 입력된다. 그리고 나서 대기화면으로 넘어가게 된다. 상대방이 나와 자기 번호를 입력하고 연결하기를 누르게 되면 역시나 addListenerForSingleValueEvent에서 상대 번호가 있는지 확인한 후 있다면 
status 아래 confirm의 값을 "yes"로 바꾼다. 대기화면에 있던 나의 앱은 addValueEventListener 메서드를 통해 "yes"로 바뀐 것을 감지하고 다음 Activity로 넘어가게 된다.

```java
public class PhoneConnectActivity extends AppCompatActivity {
    /**
     * 코드 생략
    **/
    tempRoomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(friendNumber)){
                    Toast.makeText(PhoneConnectActivity.this, "가진 사용자 없음", Toast.LENGTH_LONG).show();
                    dataSnapshot.child(myNumber).child("friendNumber").getRef().setValue(friendNumber);
                    dataSnapshot.child(myNumber).child("status").child("confirm").getRef().setValue("none");
                    String chatRoom = myNumber + friendNumber + "chat";
                    String photoRoom = myNumber + friendNumber + "photo";

                    userRef.child(tempkey).child("roomID").child("id").setValue(chatRoom);
                    userRef.child(tempkey).child("photoID").child("id").setValue(photoRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "chatroom", chatRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "myNum", myNumber);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "photoroom", photoRoom);

                    moveToAcceptActivity(myNumber, friendNumber);

                }else{
                    Toast.makeText(PhoneConnectActivity.this, "가진 사용자 있음", Toast.LENGTH_LONG).show();
                    dataSnapshot.child(friendNumber).child("status").child("confirm").getRef().setValue("yes");
                    String chatRoom = friendNumber+ myNumber + "chat";
                    String photoRoom = friendNumber + myNumber + "photo";
                    userRef.child(tempkey).child("roomID").child("id").setValue(friendNumber+ myNumber + "chat");
                    userRef.child(tempkey).child("photoID").child("id").setValue(photoRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "chatroom", chatRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "myNum", myNumber);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, "photoroom", photoRoom);
                    moveToProfileActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

```
```java

public class AcceptActivity extends AppCompatActivity {
    public void goToProfileActivity() {
        /**
         * 코드 생략
        **/
        myNumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if ("yes".equals(snapshot.getValue(String.class))) {
                        Intent intent = new Intent(AcceptActivity.this, ProfileActivity.class);
                        intent.putExtra("tempkey2", tempkey);
                        Toast.makeText(AcceptActivity.this, "연결되었습니다", Toast.LENGTH_SHORT).show();
                        countDown.cancel();
                        startActivity(intent);
                    }
                    Toast.makeText(AcceptActivity.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
```

#### 3. 1 대 1 채팅 구현
- #### firebase DB의 데이터 모습과 실제 채팅 화면
![pic7](https://github.com/jis1218/Between_Share/blob/master/img/pic7.png)<br>

- #### chatRoom이란 Node 아래 '나의 번호 + 상대 번호 + chat' 형태로 node를 만들고 그 아래에는 채팅을 주고 받을 때마다 time stamp 형식으로 node가 생성된다. 그리고 그 아래에는 메시지 내용, 날짜, 시간, 프로필사진 url, 상대방 번호를 저장한다. (time stamp에 매번 저장하게 되면 프로필 사진이 바뀔 경우에 예전 채팅과 현재 채팅의 프로필 사진이 일치하지 않을 수도 있으므로 따로 user에서 불러오는 것이 더 좋을 듯 하다.)

- #### 채팅 내용은 갱신이 될 때마다 addValueEvent가 호출이 되어 ArrayList에 담겨 RecyclerView로 뿌려주게 된다.

```java
 // ArrayList에 채팅 내용을 담아 RecyclerView에 뿌려준다.
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
                recyclerViewChatting.scrollToPosition(list.size() - 1); //chat이 추가될 때마다 RecyclerView의 스크롤을 밑으로 내려줌
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
```

- #### UI에서 나의 채팅과 상대방 채팅을 다르게 위치시키기 위해 viewType을 다르게 하여 inflate 되는 layout이 다르게 하였다.
```java
    @Override
    public int getItemViewType(int position) {

        /**
         * SharedPreference에 저장된 나의 정보와 채팅에 담겨있는 user_num이 같을 경우 MY_MESSAGE를 반환한다.
         * 아닐 경우 YOUR_MESSAGE를 반환한다.
         */
        if ((list.get(position).user_num).equals(PreferenceUtil.getStringValue(context,"myNum"))) {
            return MY_MESSAGE;
        } else {
            return YOUR_MESSAGE;
        }
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        // ViewType에 따라 어떤 view를 뿌려줄지 정한다.
        if (viewType == MY_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_chat_box, parent, false);
            return new MyAdapter(view);
        } else if (viewType == YOUR_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_chat_box, parent, false);
            return new MyAdapter(view);
        }

        return null;
    }
```

- #### 채팅의 날짜가 다를 경우 "---------날짜---------"의 형태가 나오게 하였는데 viewHolder에 구현을 해놓고 시간이 다를 경우에만 VISIBLE이 되게 하였고 나머지의 경우에는 GONE이 되게 하였다.
```java
    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        holder.textViewMyChat.setText(list.get(position).message);
        holder.textViewTime.setText(list.get(position).messageTime);
        holder.profileUrl = list.get(position).profileUrl;
        URL url = null;
        try {
            url = new URL(holder.profileUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Glide.with(context).load(url).into(holder.imageProfile);


        holder.textViewDivideLine.setText("------------------------ " + list.get(position).messageDate + "-----------------------");
        if(position>=1 && list.get(position).messageDate.equals(list.get(position-1).messageDate)){
            holder.textViewDivideLine.setVisibility(View.GONE);
        }else{
            holder.textViewDivideLine.setVisibility(View.VISIBLE);
        }
    }
```

## 그 밖에 배운 점

- #### SoftKeyboard 떴을 때 화면도 같이 위로 올라가는 방법 (그래야 UI를 가리지 않음)
 ##### manifest에 해당 activity 아래 다음과 같이 적는다
 ```
 <activity android:name=".ChattingActivity"
            android:windowSoftInputMode="adjustPan|stateHidden">
```
adjustPan을 해야 적용이 되고 stateHidden은 activity를 처음 띄웠을 때 SoftKeyboard가 보이지 않는다.
위와 같은 방법은 정적인 방법이고 동적인 방법은 java 코드에 직접 써서 해당 조건이 됐을 때 실행시키는 방법임

##### 채팅 할 때마다 새로운 채팅을 보여주기 위해 스크롤이 밑으로 내려가는 방법은
##### data를 받을 때마다 recyclerView의 scrollToPosition 함수에 인자값으로 list의 size()-1을 하면 된다.
```java
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
                recyclerViewChatting.scrollToPosition(list.size()-1);
            }
```














