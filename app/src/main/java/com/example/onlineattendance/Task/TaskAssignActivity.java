package com.example.onlineattendance.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.onlineattendance.MeetingActivity;
import com.example.onlineattendance.R;

import java.util.Calendar;
import java.util.List;

public class TaskAssignActivity extends AppCompatActivity {
    LinearLayout linearLayout,task_schedule;
    LinearLayout add_lnr;
    ImageView back;
    LinearLayout taskbar;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assign);

        taskbar = findViewById(R.id.taskbar);
        submit = findViewById(R.id.post);


        linearLayout = (LinearLayout) findViewById(R.id.lnr);
        add_lnr = findViewById(R.id.add_lnr);
        back = findViewById(R.id.back);
        task_schedule = findViewById(R.id.task_schedule_lnr);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addView();
            }
        });
        }


    public void addView() {


        View add = getLayoutInflater().inflate(R.layout.task_assign_recycle,null,false);

        LinearLayout remove_lnr;

        remove_lnr = add.findViewById(R.id.remove_lnr);


        remove_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;

                dialog = new Dialog(TaskAssignActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.alert_dailogbox);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                TextView title = dialog.findViewById(R.id.title);
                RelativeLayout no = dialog.findViewById(R.id.no);
                RelativeLayout yes = dialog.findViewById(R.id.yes);

                title.setText("Are you sure want to remove this task");


                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        removeView(add);
                    }
                });



            }
        });

        linearLayout.addView(add);

    }

    public void removeView(View view){

        linearLayout.removeView(view);

    }

}