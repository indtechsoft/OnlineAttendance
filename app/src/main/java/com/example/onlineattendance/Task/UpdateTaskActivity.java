package com.example.onlineattendance.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.onlineattendance.R;


public class UpdateTaskActivity extends AppCompatActivity {
    LinearLayout update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        update = findViewById(R.id.update_lnr);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateTaskActivity.this,ViewTaskActivity.class);
                startActivity(intent);
            }
        });

    }
}