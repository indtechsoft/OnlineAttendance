package com.example.onlineattendance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;

public class ImageCaptureActivity extends AppCompatActivity {

    ImageView image_upload,image_btn,back;
    int CAMERA_REQUEST1 = 1;
    int SELECT_PHOTO = 2;
    String currentphoto;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        image_upload = findViewById(R.id.post_image);
        image_btn = findViewById(R.id.post_btn);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        image_upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(ImageCaptureActivity.this);
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
                            File imagefile = File.createTempFile(filename,".png",storagedirectory);
                            currentphoto = imagefile.getAbsolutePath();
                            Uri imageuri = FileProvider.getUriForFile(ImageCaptureActivity.this,"com.example.onlineattendance.provider",imagefile);

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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                image_upload.setImageBitmap(bitmap);
                image_btn.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST1 && resultCode == RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(currentphoto);

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            Matrix mtx = new Matrix();
            mtx.postRotate(90);

            Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
            BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);
            image_upload.setImageDrawable(bmd);
            image_btn.setVisibility(View.GONE);

        }



        }

}