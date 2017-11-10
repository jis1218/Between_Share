package com.project.between.AnniversaryAndCalendar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.between.AnniversaryAndCalendar.vo.AnniversaryListVO;
import com.project.between.R;
import com.project.between.util.AnniversaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHYUN on 2017-11-10.
 */

public class AnniversaryCustomListAdapter extends RecyclerView.Adapter<AnniversaryCustomListAdapter.CustomHolder> {
    List<AnniversaryListVO> data = new ArrayList<>();

    public AnniversaryCustomListAdapter(){
    }

    public void dataSetChanged(List<AnniversaryListVO> data){
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_anniversary_list_custom_day, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        AnniversaryListVO vo = data.get(position);
        holder.tvCustomTitle.setText(vo.getTitle());
        holder.tvCustomDate.setText(AnniversaryUtil.makeDateString(vo.getDate()));
        holder.tvCustomDday.setText(vo.getDday());
        if(vo.getHomeYn().equals("true")){
            holder.ivCheck.setImageResource(android.R.drawable.checkbox_on_background);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        TextView tvCustomTitle, tvCustomDday, tvCustomDate;
        ImageView ivCheck;

        public CustomHolder(View itemView) {
            super(itemView);
            tvCustomTitle = itemView.findViewById(R.id.tvCustomTitle);
            tvCustomDday = itemView.findViewById(R.id.tvCustomDday);
            tvCustomDate = itemView.findViewById(R.id.tvCustomDate);
            ivCheck = itemView.findViewById(R.id.ivCheck);
        }
    }
}
