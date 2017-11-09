package com.project.between.AnniversaryAndCalendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainFragment extends Fragment {

    @Override
    public void onAttach(Context context) { // <- 이 컨텍스트가 나를 삽입한 액티비티이다.
        // 1. 나를 사용한 액티비티가 내가 제공한 인터페이스를 구현했는지 확인
        if(context instanceof Callback){
            callback = (Callback) context; // 2. 구현했다면 인터페이스로 캐스팅해서 사용
        }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d("Fragment","==========onDetach()");
        super.onDetach();
    }

    Callback callback = null;

    Button goDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.fragment_list, container, false);
        goDetail = (Button) view.findViewById(R.id.button);
        goDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.goDetail(); // 내가 설계해 놓은 인터페이스를 호출한다
                // 나를 사용하는 측은 이 인터페이스를 강제로 구현해야 한다.
            }
        });*/
        Log.e("test","---------");
        return null;
    }

    public interface Callback {
//        public void goDetail();
    }

    @Override
    public void onStart() {
        Log.d("Fragment","==========onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("Fragment","==========onResume()");
        super.onResume();
    }
}
