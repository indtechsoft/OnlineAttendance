package com.example.onlineattendance.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.onlineattendance.R;
import com.example.onlineattendance.Task.ViewTaskActivity;
import com.example.onlineattendance.Task.ViewTaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    List<NotificationModel> notificationModelList;
    NotificationAdapter notificationAdapter;
    RecyclerView recyclerView;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.notification_recycle);
        back = findViewById(R.id.back);

        notificationModelList = new ArrayList<>();
        for(int i=0;i<=10;i++){
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setNotification_title("Indtechsoft");
            notificationModel.setNotification_text("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

            notificationModelList.add(notificationModel);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        notificationAdapter = new NotificationAdapter(NotificationActivity.this, notificationModelList);
        recyclerView.setAdapter(notificationAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}