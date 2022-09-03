package com.example.onlineattendance;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineattendance.History.HistoryActivity;
import com.example.onlineattendance.Navigation.LeadActivity;
import com.example.onlineattendance.Notification.NotificationActivity;
import com.example.onlineattendance.Notification.NotificationModel;
import com.example.onlineattendance.Task.TaskAssignActivity;
import com.example.onlineattendance.Task.ViewTaskActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardActivity extends AppCompatActivity {

    LinearLayout day_in,day_out,check_in, check_out,meeting_lnr,location_history;
    LinearLayout lunch,tea,travel,meeting,break_time,leave,permission;
    private boolean isblue = true;
    private static final int REQUEST_CALL = 1;
    Button submit;
    LinearLayout logout, notification;
    private Geocoder geocoder;
    FloatingActionButton image_upload;
    int CAMERA_REQUEST1 = 1;
    int SELECT_PHOTO = 2;
    String currentphoto;
    Bitmap bitmap;
    CircleImageView avatar;
    LinearLayout location_lnr,capture_lnr,task_lnr;
    int resId;
    TextView location_txt,headline;
    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();
    String lat, longi;
    private boolean gps_enable = false;
    private boolean network_enable = false;
    List<Address> myaddress;
    Drawable d_lunch,d_tea,d_travel,d_meeting,d_break,d_leave,d_permission;
    LinearLayout saved_location,radio_lnr;
    Button choose_btn,save_btn;
    RadioButton home,work,other;
    EditText address_edit;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView home_txt;
    LinearLayout task_schedule_lnr,call;
    String phone;
    Dialog dialog;
    NavigationView navigationView;
    ImageView menu;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        navigationView = findViewById(R.id.nav_view);
        day_in = findViewById(R.id.intime_btn);
        day_out = findViewById(R.id.outime_btn);
        submit = findViewById(R.id.submit_btn);
        logout = findViewById(R.id.logout_lnr);
        notification = findViewById(R.id.notification);
        check_in = findViewById(R.id.checkin_btn);
        check_out = findViewById(R.id.checkout_btn);
        image_upload = findViewById(R.id.upload_img);
        avatar = findViewById(R.id.avatar);
        location_lnr = findViewById(R.id.location_lnr);
        location_txt = findViewById(R.id.location_txt);
        capture_lnr = findViewById(R.id.capture_lnr);
        meeting_lnr = findViewById(R.id.meeting_lnr);
        task_lnr = findViewById(R.id.task_lnr);
        headline = findViewById(R.id.headline);
        task_schedule_lnr = findViewById(R.id.task_schedule_lnr);
        location_history = findViewById(R.id.location_history);
        call = findViewById(R.id.call_btn);



        saved_location = findViewById(R.id.saved_location);
        choose_btn = findViewById(R.id.address_btn);
        save_btn = findViewById(R.id.save_btn);
        home = findViewById(R.id.home);
        work = findViewById(R.id.work);
        other = findViewById(R.id.other);
        address_edit = findViewById(R.id.address_edit);
        radio_lnr = findViewById(R.id.radio_lnr);
        radioGroup = findViewById(R.id.radiogroup);
        home_txt = findViewById(R.id.home_txt);
        menu = findViewById(R.id.menu);


        saved_location.setVisibility(View.GONE);
        choose_btn.setVisibility(View.GONE);
        save_btn.setVisibility(View.GONE);
        radio_lnr.setVisibility(View.GONE);
        address_edit.setVisibility(View.GONE);

        lunch = findViewById(R.id.lunch);
        tea = findViewById(R.id.tea);
        meeting = findViewById(R.id.meeting);
        break_time = findViewById(R.id.break_time);
        travel = findViewById(R.id.travel);
        leave = findViewById(R.id.leave);
        permission = findViewById(R.id.permission);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        checklocationpermission();

        DrawerLayout drawerLayout = findViewById(R.id.drawerr_layout);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DashboardActivity.this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();


                switch (id) {

                    case R.id.Lead:
                        Intent home = new Intent(DashboardActivity.this, LeadActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(home);
                        break;

                }


                return true;

            }

        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
        location_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, HistoryActivity.class));
            }
        });

        task_schedule_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,TaskAssignActivity.class);
                intent.putExtra("update","task");
                startActivity(intent);
            }
        });

        task_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ViewTaskActivity.class));

            }
        });

        meeting_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,MeetingActivity.class));
            }
        });

        capture_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,ImageCaptureActivity.class));
            }
        });




        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_box();
            }
        });
        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_box();

            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();

            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();
            }
        });
        break_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();


            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();


            }
        });

        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();


            }
        });





        image_upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(DashboardActivity.this);

                dialog.setContentView(R.layout.choose_image);

                TextView close = dialog.findViewById(R.id.close);
                TextView file = dialog.findViewById(R.id.file);
                TextView camera = dialog.findViewById(R.id.camera);


                file.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, SELECT_PHOTO);
                    }
                });


                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();


                        String filename = "Photo";
                        File storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        try {
                            File imagefile = File.createTempFile(filename,".jpg",storagedirectory);
                            currentphoto = imagefile.getAbsolutePath();
                            Uri imageuri = FileProvider.getUriForFile(DashboardActivity.this,"com.example.onlineattendance.provider",imagefile);

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                            startActivityForResult(intent,CAMERA_REQUEST1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                dialog.setCancelable(false);

                dialog.show();
            }
        });




        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getlocation();

            }
        });

        check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getlocation();


            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, NotificationActivity.class));

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ConfirmActivity.class));

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;

                dialog = new Dialog(DashboardActivity.this);

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

                title.setText("Are you sure want to logout");


                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                    }
                });


            }
        });
        day_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(DashboardActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.time_popup);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                RelativeLayout yes = dialog.findViewById(R.id.ok);


                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
            }
        });
        day_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(DashboardActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.time_popup);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                RelativeLayout yes = dialog.findViewById(R.id.ok);


                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
            }
        });
    }



    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    public void dialog_box( ){

        Dialog dialog;

        dialog = new Dialog(DashboardActivity.this);

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


        d_lunch = lunch.getBackground();
        d_tea = tea.getBackground();
        d_travel = travel.getBackground();
        d_meeting = meeting.getBackground();
        d_break = break_time.getBackground();
        d_leave = leave.getBackground();
        d_permission = permission.getBackground();



        if(lunch.isPressed()){
            if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
                else{
                title.setText("Are you sure want to enable lunch time");
            }


        }

        else if(tea.isPressed() ) {


            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable tea time");
            }



        }

        else if(travel.isPressed()){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable travel mode");
            }

        }
        else if(meeting.isPressed() ){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable meeting time");
            }

        }
        else if(break_time.isPressed()){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable break time");
            }

        }
        else if(leave.isPressed() ){

          if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable leave option");
            }
        }
        else if(permission.isPressed()){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable permission option");
            }
        }




        if(lunch.isPressed() && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable lunch time");
        }

        if(tea.isPressed()  && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable tea time");
        }

        else if(travel.isPressed() && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable travel mode");

        }

        else if(meeting.isPressed()  && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable meeting time");

        }

        else if(break_time.isPressed()  && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable break time");

        }
        else if(leave.isPressed()  && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable leave option");

        }

        else if(permission.isPressed() && resId==R.drawable.peacockblue_circle){
            title.setText("Are you sure want to disable permission option");

        }



        no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    if(lunch.isPressed() && title.getText().equals("Are you sure want to enable lunch time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        lunch.setBackgroundResource(resId);



                    }
                   else if(lunch.isPressed() && title.getText().equals("Are you sure want to disable lunch time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        lunch.setBackgroundResource(resId);



                    }
                    if(tea.isPressed() && title.getText().equals("Are you sure want to enable tea time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        tea.setBackgroundResource(resId);



                    }
                    else if(tea.isPressed() && title.getText().equals("Are you sure want to disable tea time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        tea.setBackgroundResource(resId);


                    }
                    else if(travel.isPressed() && title.getText().equals("Are you sure want to enable travel mode") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        travel.setBackgroundResource(resId);


                    }
                    else if(travel.isPressed() && title.getText().equals("Are you sure want to disable travel mode")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        travel.setBackgroundResource(resId);

                    }
                    else if(meeting.isPressed() && title.getText().equals("Are you sure want to enable meeting time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        meeting.setBackgroundResource(resId);


                    }
                    else if(meeting.isPressed() && title.getText().equals("Are you sure want to disable meeting time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        meeting.setBackgroundResource(resId);

                    }
                   else if(break_time.isPressed() && title.getText().equals("Are you sure want to enable break time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        break_time.setBackgroundResource(resId);


                    }
                    else if(break_time.isPressed() && title.getText().equals("Are you sure want to disable break time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        meeting.setBackgroundResource(resId);

                    }
                    else if(leave.isPressed() && title.getText().equals("Are you sure want to enable leave option") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        leave.setBackgroundResource(resId);


                    }
                    else if(leave.isPressed() && title.getText().equals("Are you sure want to disable leave option")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        leave.setBackgroundResource(resId);

                    }
                    else if(permission.isPressed() && title.getText().equals("Are you sure want to enable permission option") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        permission.setBackgroundResource(resId);


                    }
                    else if(permission.isPressed() && title.getText().equals("Are you sure want to disable permission option")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        permission.setBackgroundResource(resId);

                    }

                }
            });





        if(lunch.isPressed()){

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    lunch.setBackgroundResource(resId);


                }
            });

        }
        else if(tea.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    tea.setBackgroundResource(resId);
                    if(lunch.equals(resId)){
                        Toast.makeText(DashboardActivity.this, "disable", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if(travel.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    travel.setBackgroundResource(resId);


                }
            });

        }
        else if(meeting.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    meeting.setBackgroundResource(resId);


                }
            });

        }
        else if(break_time.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    break_time.setBackgroundResource(resId);


                }
            });

        }
        else if(leave.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    leave.setBackgroundResource(resId);


                }
            });

        }
        else if(permission.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    isblue = !isblue;
                    resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                    permission.setBackgroundResource(resId);


                }
            });

        }

    }



    public void onBackPressed() {


        Dialog dialog;

        dialog = new Dialog(DashboardActivity.this);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                avatar.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST1 && resultCode == RESULT_OK) {

            bitmap = BitmapFactory.decodeFile(currentphoto);
            avatar.setImageBitmap(bitmap);




        }
    }


    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {

            if (location != null) {

                locationManager.removeUpdates(locationListener);

                lat = "" + location.getLatitude();
                longi = "" + location.getLongitude();

                geocoder = new Geocoder(DashboardActivity.this,Locale.getDefault());
                try {
                    myaddress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = myaddress.get(0).getAddressLine(0);


                location_txt.setText(address);
                headline.setText("Your Current Location");
                choose_btn.setVisibility(View.VISIBLE);
                choose_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        radio_lnr.setVisibility(View.VISIBLE);
                        save_btn.setVisibility(View.VISIBLE);

                        check_btn();

                       other.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               address_edit.setVisibility(View.VISIBLE);


                           }
                       });

                       save_btn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               String Address = address_edit.getText().toString();


                               if(!home.isChecked() && !work.isChecked() && !other.isChecked()){
                                   Toast.makeText(DashboardActivity.this, "Please select one choice", Toast.LENGTH_SHORT).show();
                               }
                              else if(home.isChecked()&& address_edit.getVisibility()==View.GONE){
                                   home_txt.setText("Home");
                                   address_edit.setVisibility(View.GONE);
                                   save_btn.setVisibility(View.GONE);
                                   radio_lnr.setVisibility(View.GONE);
                                   saved_location.setVisibility(View.VISIBLE);
                                   choose_btn.setVisibility(View.GONE);
                               }
                               else if(work.isChecked()&& address_edit.getVisibility()==View.GONE){
                                   home_txt.setText("Office");
                                   address_edit.setVisibility(View.GONE);
                                   save_btn.setVisibility(View.GONE);
                                   radio_lnr.setVisibility(View.GONE);
                                   saved_location.setVisibility(View.VISIBLE);
                                   choose_btn.setVisibility(View.GONE);
                               }
                             else  if(Address.isEmpty() && address_edit.getVisibility()==View.VISIBLE){
                                   home_txt.setText("Other");
                                   address_edit.setVisibility(View.GONE);
                                   save_btn.setVisibility(View.GONE);
                                   radio_lnr.setVisibility(View.GONE);
                                   saved_location.setVisibility(View.VISIBLE);
                                   choose_btn.setVisibility(View.GONE);
                               }else {
                                   home_txt.setText(address_edit.getText().toString());
                                   address_edit.setVisibility(View.GONE);
                                   save_btn.setVisibility(View.GONE);
                                   radio_lnr.setVisibility(View.GONE);
                                   saved_location.setVisibility(View.VISIBLE);
                                   choose_btn.setVisibility(View.GONE);
                               }





                           }
                       });

                    }
                });

            }
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    public void getlocation() {

        try {
            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {

        }
        try {
            network_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }

        if (!gps_enable && !network_enable) {

           buildAlertMessageNoGps();
        }

        if (gps_enable) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        if(network_enable){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }

    }

    private boolean checklocationpermission(){

        int location = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listpermission = new ArrayList<>();

        if(location!=PackageManager.PERMISSION_GRANTED){
            listpermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if(location2!=PackageManager.PERMISSION_GRANTED){
            listpermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        }
        if(!listpermission.isEmpty()){

            ActivityCompat.requestPermissions(this,listpermission.toArray(new String[listpermission.size()]),
                    1);

        }

        return  true;

    }


    private void buildAlertMessageNoGps() {

        Dialog dialog;

        dialog = new Dialog(DashboardActivity.this);


        dialog.setContentView(R.layout.alert_dailogbox);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        TextView title = dialog.findViewById(R.id.title);
        RelativeLayout no = dialog.findViewById(R.id.no);
        RelativeLayout yes = dialog.findViewById(R.id.yes);

        title.setText("Your GPS seems to be disabled, do you want to enable it?");


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

                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


            }
        });




    }

    public void check_btn(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


    }
    private void makePhoneCall() {
       phone = "9626231757";
       String number = phone;

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(DashboardActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DashboardActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(DashboardActivity.this, "Permission Denied to make a call" + "", Toast.LENGTH_SHORT).show();
            }
        }
    }




}

