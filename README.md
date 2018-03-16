# Between

## Between 소개

- #### 안드로이드 스튜디오를 이용해 커플 채팅 앱인 Between의 기능을 따라 구현
- #### 기간 : 2017년 11월 10일 ~ 11일

## 프로젝트 목적

- #### Google Firebase를 이용해 이메일 로그인, Realtime Database(NoSQL)의 기능을 사용하여 앱 구현

## 스크린샷
![pic1](https://github.com/jis1218/Between_Share/blob/master/img/pic1.png)
![pic2](https://github.com/jis1218/Between_Share/blob/master/img/pic2.png)
![pic3](https://github.com/jis1218/Between_Share/blob/master/img/pic3.png)

## 담당한 부분 및 배운 것들
### by Insup Jung

#### 1. 회원가입 기능
- Firebase의 FirebaseAuth 클래스를 이용하여 이메일과 비밀번호를 입력받았다.

SoftKeyboard 떴을 때 화면도 같이 위로 올라가는 방법 (그래야 UI를 가리지 않음)
 - manifest에 해당 activity 아래 다음과 같이 적는다
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