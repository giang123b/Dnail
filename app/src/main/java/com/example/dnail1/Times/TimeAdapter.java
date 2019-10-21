package com.example.dnail1.Times;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dnail1.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Time> timeList;
    private int selectedPosition=-1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time);
        }
    }

    public TimeAdapter(Context mContext, List<Time> timeList) {
        this.mContext = mContext;
        this.timeList = timeList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Time time = timeList.get(position);
        holder.time.setText(time.getTime());

        if(selectedPosition==position){
//            holder.itemView.setBackgroundColor(Color.parseColor("#E91E63"));
            holder.itemView.setBackgroundResource(R.drawable.shape_round_not_full_pink);
            holder.time.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.shape_round_not_full_pink);
            holder.time.setTextColor(mContext.getResources().getColor(R.color.pink_theme));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return timeList.size();
    }
}