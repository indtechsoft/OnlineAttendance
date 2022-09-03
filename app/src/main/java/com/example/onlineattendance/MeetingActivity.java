package com.example.onlineattendance;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.onlineattendance.History.HistoryActivity;

import java.util.Calendar;

public class MeetingActivity extends AppCompatActivity {

    LinearLayout linearLayout,task_schedule,edit_lnr;
    LinearLayout add_lnr;
    int start_hour,start_minute,end_hour,end_minute;
    Calendar calendar;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);


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


        View add = getLayoutInflater().inflate(R.layout.meeting_entries,null,false);

          RelativeLayout date_relative,start_relative,end_relative;
          TextView date,start,end;
          EditText description;
          LinearLayout remove_lnr,circle_add;
          Button add_btn;



          date_relative = add.findViewById(R.id.date_relative);
          start_relative =add.findViewById(R.id.start_time_relative);
          end_relative =add.findViewById(R.id.end_time_relative);
          description = add.findViewById(R.id.description);
          remove_lnr = add.findViewById(R.id.remove_lnr);
          circle_add = add.findViewById(R.id.circle_add);
          edit_lnr = add.findViewById(R.id.edit_lnr);

          date =add.findViewById(R.id.date_txt);
          start =add.findViewById(R.id.start_time_txt);
          end =add.findViewById(R.id.end_time_txt);
          add_btn = add.findViewById(R.id.add_btn);




          circle_add.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  View edit = getLayoutInflater().inflate(R.layout.edittext_layout,null,false);

                  EditText name;
                  ImageView delete;

                  name = edit.findViewById(R.id.name);
                  delete = edit.findViewById(R.id.delete);

                  add_btn.setVisibility(View.VISIBLE);

                  delete.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {

                          Dialog dialog;

                          dialog = new Dialog(MeetingActivity.this);

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

                          title.setText("Are you sure want to remove this name");


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
                                  remove(edit);
                                  if(edit_lnr.getChildCount()>0){
                                      add_btn.setVisibility(View.VISIBLE);


                                  }else{
                                      add_btn.setVisibility(GONE);

                                  }
                              }
                          });

                      }
                  });

                  edit_lnr.addView(edit);

              }
          });



          date_relative.setOnClickListener(new View.OnClickListener() {
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


                          date.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);



                      }
                  };

                  new DatePickerDialog(MeetingActivity.this, dateA, calendar.get(Calendar.YEAR),
                          calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
              }
          });

          start_relative.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  TimePickerDialog timePickerDialog = new TimePickerDialog(MeetingActivity.this,
                          new TimePickerDialog.OnTimeSetListener() {
                              @Override
                              public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                                  start_hour = hour;
                                  start_minute = minute;
                                  Calendar calendar = Calendar.getInstance();
                                  calendar.set(0,0,0,start_hour,start_minute);
                                  start.setText(DateFormat.format("hh:mm aa",calendar));



                              }
                          },12,0,false);

                  timePickerDialog.updateTime(start_hour,start_minute);
                  timePickerDialog.show();
              }
          });

          end_relative.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  TimePickerDialog timePickerDialog = new TimePickerDialog(MeetingActivity.this,
                          new TimePickerDialog.OnTimeSetListener() {
                              @Override
                              public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                                  end_hour = hour;
                                  end_minute = minute;
                                  Calendar calendar = Calendar.getInstance();
                                  calendar.set(0,0,0,end_hour,end_minute);
                                  end.setText(DateFormat.format("hh:mm aa",calendar));



                              }
                          },12,0,false);

                  timePickerDialog.updateTime(end_hour,end_minute);
                  timePickerDialog.show();
              }
          });



          remove_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;

                dialog = new Dialog(MeetingActivity.this);

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

                title.setText("Are you sure want to remove this schedule");


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

    public void remove(View view){

        edit_lnr.removeView(view);

    }




}