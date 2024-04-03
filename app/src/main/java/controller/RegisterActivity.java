package controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapp.DatabaseHelper;
import com.example.hotelbookingapp.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername, edtMail, edtPassword, edtPhone;
    Button btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsername);
        edtMail = findViewById(R.id.edtMail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        dbHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String email = edtMail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

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
