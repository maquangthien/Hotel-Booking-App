package com.example.hotelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameEditText, emailEditText, passwordEditText, phoneEditText;
    Button registerButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        dbHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                // Thêm người dùng vào cơ sở dữ liệu
                long userId = dbHelper.addUser(username, email,phone, password, 3);

                if (userId != -1) {
                    // Người dùng đã được đăng ký thành công
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    // Đưa người dùng đến màn hình đăng nhập hoặc màn hình chính
                    // Ví dụ:
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Đóng RegisterActivity sau khi chuyển đến màn hình khác
                } else {
                    // Đã xảy ra lỗi khi đăng ký người dùng
                    Toast.makeText(RegisterActivity.this, "Đã xảy ra lỗi khi đăng ký người dùng", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Yêu cầu người dùng nhập đầy đủ thông tin
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
