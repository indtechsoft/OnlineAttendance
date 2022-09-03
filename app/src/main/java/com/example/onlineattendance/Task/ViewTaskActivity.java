package com.example.onlineattendance.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineattendance.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewTaskActivity extends AppCompatActivity {
    List<ViewTaskModel> taskModelList;
    ViewTaskAdapter taskAdapter;
    RecyclerView recyclerView;
    ImageView calender_img;
    Calendar calendar;
    TextView date_txt;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView = findViewById(R.id.task_list);

        calender_img = findViewById(R.id.calender_img);
        date_txt = findViewById(R.id.date_txt);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        calender_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateA = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        date_txt.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);


                    }
                };

                new DatePickerDialog(ViewTaskActivity.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        taskModelList = new ArrayList<>();
        for(int i=0;i<=10;i++){
            ViewTaskModel taskModel = new ViewTaskModel();

            taskModel.setTask_id("01");
            taskModel.setTask_name("Image Uploading");
            taskModel.setTask_assigned_by("Admin");
            taskModel.setTask_assigned_to("Employee");
            taskModel.setStatus("Completed");

            taskModelList.add(taskModel);

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewTaskActivity.this));
        taskAdapter = new ViewTaskAdapter(ViewTaskActivity.this, taskModelList);
        recyclerView.setAdapter(taskAdapter);


    }
}