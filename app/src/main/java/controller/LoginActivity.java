package controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hotelbookingapp.AdminMainActivity;
import com.example.hotelbookingapp.CustomerMainActivity;
import com.example.hotelbookingapp.DatabaseHelper;
import com.example.hotelbookingapp.EmployeeMainActivity;
import com.example.hotelbookingapp.R;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        dbHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                int roleId = dbHelper.getUserRoleId(username, password); // Lấy roleId của người dùng

                if (roleId != -1) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    switch (roleId) {
                        case 1: // Admin
                            Intent adminIntent = new Intent(LoginActivity.this, AdminMainActivity.class);
                            startActivity(adminIntent);
                            break;
                        case 2: // Employee
                            Intent employeeIntent = new Intent(LoginActivity.this, EmployeeMainActivity.class);
                            startActivity(employeeIntent);
                            break;
                        case 3: // Customer
                            Intent customerIntent = new Intent(LoginActivity.this, CustomerMainActivity.class);
                            startActivity(customerIntent);
                            break;
                        default:
                            break;
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tên người dùng hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onRegisterClick(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

