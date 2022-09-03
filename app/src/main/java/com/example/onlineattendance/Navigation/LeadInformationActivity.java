package com.example.onlineattendance.Navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.onlineattendance.R;
import com.example.onlineattendance.Task.TaskAssignActivity;

public class LeadInformationActivity extends AppCompatActivity {
    LinearLayout add_lnr;
    ImageView back;
    Spinner property_cat,property_reg;
    Button submit;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_information);

        add_lnr = findViewById(R.id.add_lnr);
        back = findViewById(R.id.back);
        property_cat = findViewById(R.id.property_cat);
        property_reg = findViewById(R.id.property_reg);
        submit = findViewById(R.id.submit);
        relativeLayout = findViewById(R.id.relate);

        relativeLayout.setVisibility(View.GONE);

        ArrayAdapter<String> Adapter1 = new ArrayAdapter<String>(LeadInformationActivity.this,
                R.layout.spinner_text, getResources().getStringArray(R.array.property_category));
        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        property_cat.setAdapter(Adapter1);



        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(LeadInformationActivity.this,
                R.layout.spinner_text, getResources().getStringArray(R.array.property_region));
        Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        property_reg.setAdapter(Adapter2);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        add_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                relativeLayout.setVisibility(View.VISIBLE);

            }
        });

    }


}