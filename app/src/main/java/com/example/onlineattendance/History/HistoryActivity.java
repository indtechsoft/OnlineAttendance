package com.example.onlineattendance.History;

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

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HistoryModel> historyModelList;
    HistoryAdapter historyAdapter;
    ImageView back,calender_img;
    Calendar calendar;
    TextView date_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.history_list);
        back = findViewById(R.id.back);
        calender_img = findViewById(R.id.calender_img);
        date_txt = findViewById(R.id.date_txt);

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

                new DatePickerDialog(HistoryActivity.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        historyModelList = new ArrayList<>();

        for(int i=0;i<=10;i++){
            HistoryModel historyModel = new HistoryModel();

            historyModel.setLocation("291 A, I Floor, 5th St Ext, behind Kalyan Jewellers, Coimbatore, TamilNadu 641012");
            historyModel.setTime("9 : 30 A.M");

            historyModelList.add(historyModel);

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        historyAdapter = new HistoryAdapter(HistoryActivity.this, historyModelList);
        recyclerView.setAdapter(historyAdapter);






    }
}