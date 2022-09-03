package com.example.onlineattendance;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);


        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));

            }
        });
    }
    public void onBackPressed() {


        Dialog dialog;

        dialog=new Dialog(LoginActivity.this);

        // set custom dialog
        dialog.setContentView(R.layout.alert_dailogbox);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // show dialog
        dialog.show();

        // Initialize and assign variable
        TextView title=dialog.findViewById(R.id.title);
        RelativeLayout no=dialog.findViewById(R.id.no);
        RelativeLayout yes=dialog.findViewById(R.id.yes);

        title.setText("Are you sure want to exit");


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();
            }
        });

    }
}