package com.example.onlineattendance;














import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        new Handler().postDelayed(new Runnable() {
            @Override

            public void run() {

                    Intent i = new Intent(SplashActivity.this, GetStartedActivity.class);
                    startActivity(i);

            }
        }, 3350);

    }
}