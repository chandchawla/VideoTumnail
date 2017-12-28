package com.task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static int MEDIA_TYPE_VIDEO;

    private static File mediaStorageDir;
    ImageView img;
    Button bu;
    Bitmap bmThumbnail;
    private Uri videoUri;
    private int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu = (Button)findViewById(R.id.goToRoom);
        img = (ImageView)findViewById(R.id.img);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
            }
        });
    }
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }


    private static File getOutputMediaFile(int type){
         mediaStorageDir = new File(Environment.getExternalStorageDirectory() +"/CsVideos");
        if (!mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }
        java.util.Date date= new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd").format(date.getTime());
        File mediaFile;
        if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_"+ timeStamp + ".mp4");
        }
        else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                videoUri = data.getData();
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "User cancelled the video capture",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Video capture failed.", Toast.LENGTH_LONG).show();
            }
        }
    }





}
