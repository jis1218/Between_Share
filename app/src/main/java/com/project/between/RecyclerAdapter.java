package com.project.between;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.between.domain.MyMessage;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-06.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyAdapter> {
    ArrayList<MyMessage> list = new ArrayList<>();
    final static String MY_NUM = "010010010";
    final static int MY_MESSAGE = 0;
    final static int YOUR_MESSAGE = 1;


    public void refreshData(ArrayList<MyMessage> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (MY_NUM.equals(list.get(position).phone_number)) {
            return MY_MESSAGE;
        } else {
            return YOUR_MESSAGE;
        }
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MY_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_chat_box, parent, false);
            return new MyAdapter(view);
        } else if (viewType == YOUR_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_chat_box, parent, false);
            return new MyAdapter(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        holder.textViewMyChat.setText(list.get(position).message);
        holder.textViewTime.setText(list.get(position).messageTime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyAdapter extends RecyclerView.ViewHolder {
        TextView textViewMyChat;
        TextView textViewTime;

        public MyAdapter(View itemView) {
            super(itemView);

            textViewMyChat = itemView.findViewById(R.id.textViewMyChat);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
