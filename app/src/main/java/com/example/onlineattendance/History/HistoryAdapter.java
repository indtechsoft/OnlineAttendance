package com.example.onlineattendance.History;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineattendance.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<HistoryModel> historyModelList;
    Context context;





    public HistoryAdapter(Context context, List<HistoryModel> historyModelList) {

        this.historyModelList = historyModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycle, parent, false);


        return new HistoryAdapter.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
                return historyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView location,time;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            location = itemView.findViewById(R.id.location_text);
            time = itemView.findViewById(R.id.time_txt);



        }


    }
}