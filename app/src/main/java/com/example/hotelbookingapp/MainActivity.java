package com.example.hotelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import controller.LoginActivity;
import controller.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    Button register, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        handler.postDelayed( new Runnable(){
            @Override
            public void run(){
                Intent intentRun = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentRun);
                finish();
            }
        }, 3000);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        register = findViewById(R.id.btnRegister);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//                startActivity(intent);
//            }
//        });

//        login = findViewById(R.id.btnLogin);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent1);
//            }
//        });
    }
}