package com.example.onlineattendance.Task;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineattendance.R;

import java.util.List;

public class ViewTaskAdapter extends RecyclerView.Adapter<ViewTaskAdapter.ViewHolder> {

    List<ViewTaskModel> taskModelList;
    Context context;



    public ViewTaskAdapter(Context context, List<ViewTaskModel> taskModelList) {

        this.taskModelList = taskModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycle, parent, false);


        return new ViewTaskAdapter.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTaskAdapter.ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
                return taskModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView task_id,task_name,task_assigned_to,task_assigned_by,status;
        ImageView update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task_id = itemView.findViewById(R.id.task_id);
            task_name = itemView.findViewById(R.id.task_name);
            task_assigned_to = itemView.findViewById(R.id.task_assigned_to);
            task_assigned_by = itemView.findViewById(R.id.task_assigned_by);
            status = itemView.findViewById(R.id.status);

            update = itemView.findViewById(R.id.update_img);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),UpdateTaskActivity.class);
                    view.getContext().startActivity(intent);


                }
            });


        }


    }
}