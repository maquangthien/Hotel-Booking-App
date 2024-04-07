package com.example.hotelbookingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class cloudary extends AppCompatActivity {

    private static final String TAG = "Upload image";
    private ImageView imageView;
    private Button btnUpImage;


    private Uri imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cloudary);
        imageView = findViewById(R.id.imageView);
        btnUpImage = findViewById(R.id.btnUpImage);
        initCloud();
        imageView.setOnClickListener(v -> {
            requestPermission();

        });
        btnUpImage.setOnClickListener(v -> {
            Log.d(TAG, ": "+" button clicked");
            MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onstart: "+"started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onstart: "+"uploading");
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    Log.d(TAG, "onstart: "+"success");
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onstart: "+error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onstart: "+error);
                }

            }).dispatch();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initCloud(){
        Map config = new HashMap();
        config.put("cloud_name", "dtcjtwznh");
        config.put("api_key", "137131721752293");
        config.put("api_secret", "2piWuAlUnTFlpWCaDI8BKiObYoo");
//        config.put("secure", true);
        MediaManager.init(this, config);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(cloudary.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions(cloudary.this, new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*"); // chuẩn hình ảnh đc set
        intent.setType("mp4/*");




        intent.setAction(Intent.ACTION_GET_CONTENT);

        someActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imagePath =  data.getData();
                        Picasso.get()
                                .load(imagePath)
                                .into(imageView);

                    }
                }
            });



}