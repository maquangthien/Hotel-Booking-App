package com.example.hotelbookingapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import controller.AccountActivity;
import database.DatabaseHelper;

public class CustomerMainActivity extends AppCompatActivity {

    ImageButton account_button;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_main);
        dbHelper = new DatabaseHelper(this);

        // ...

        account_button = findViewById(R.id.account_button);
        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int userId =1;
                Cursor cursor = dbHelper.getUserInfoById(userId);
                cursor.moveToFirst();
                if (cursor != null ) {
                    int usernameIndex = cursor.getColumnIndex(DatabaseHelper.getColumnUsername());
                    int emailIndex = cursor.getColumnIndex(DatabaseHelper.getColumnEmail());
                    int phoneIndex = cursor.getColumnIndex(DatabaseHelper.getColumnPhone());
                    int dobIndex = cursor.getColumnIndex(DatabaseHelper.getColumnDob());

                    if (usernameIndex >= 0 && emailIndex >= 0 && phoneIndex >= 0 && dobIndex >= 0) {
                        Intent intent = new Intent(CustomerMainActivity.this, AccountActivity.class);

                        intent.putExtra("username", cursor.getString(usernameIndex));
                        Log.d(TAG, "usernameIndex: "+usernameIndex);
                        intent.putExtra("email", cursor.getString(emailIndex));
                        Log.d(TAG, "emailIndex: "+emailIndex);
                        intent.putExtra("phone", cursor.getString(phoneIndex));
                        Log.d(TAG, "phoneIndex: "+phoneIndex);
                        intent.putExtra("dob", cursor.getString(dobIndex));
                        Log.d(TAG, "dobIndex: "+dobIndex);
                        startActivity(intent);
                        Log.d(TAG, "nhôn cái l");
                    } else {
                        Toast.makeText(CustomerMainActivity.this, "Không thể tải thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CustomerMainActivity.this, "Không thể tải thông tin người dùng", Toast.LENGTH_SHORT).show();
                }

                // Đóng con trỏ
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }
}
