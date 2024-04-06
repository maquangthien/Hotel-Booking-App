package com.example.hotelbookingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
                int userId = 1;
                Cursor cursor = dbHelper.getUserInfoById(userId);

                if (cursor != null && cursor.moveToFirst()) {
                    int usernameIndex = cursor.getColumnIndex(DatabaseHelper.getColumnUsername());
                    int emailIndex = cursor.getColumnIndex(DatabaseHelper.getColumnEmail());
                    int phoneIndex = cursor.getColumnIndex(DatabaseHelper.getColumnPhone());
                    int dobIndex = cursor.getColumnIndex(DatabaseHelper.getColumnDob());

                    if (usernameIndex >= 0 && emailIndex >= 0 && phoneIndex >= 0 && dobIndex >= 0) {
                        Intent intent = new Intent(CustomerMainActivity.this, AccountActivity.class);
                        intent.putExtra("username", cursor.getString(usernameIndex));
                        intent.putExtra("email", cursor.getString(emailIndex));
                        intent.putExtra("phone", cursor.getString(phoneIndex));
                        intent.putExtra("dob", cursor.getString(dobIndex));
                        startActivity(intent);
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
