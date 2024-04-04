package controller;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import database.DatabaseHelper;
import com.example.hotelbookingapp.R;

public class AccountActivity extends AppCompatActivity {
    EditText edtUsername, edtMail, edtPhone, edtDOB, edtPassword;
    Button btnSave;
    DatabaseHelper databaseHelper;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edtUsername);
        edtMail = findViewById(R.id.edtMail);
        edtPhone = findViewById(R.id.edtPhone);
        edtDOB = findViewById(R.id.edtDOB);
        edtPassword = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        // Lấy user ID từ Intent hoặc bundle
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Load thông tin người dùng khi mở hoạt động
        loadUserInfo();

        // Xử lý sự kiện khi nhấn nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code xử lý khi nhấn nút Lưu
            }
        });
    }

    private void loadUserInfo() {
        Cursor cursor = databaseHelper.getUserInfoById(userId);
        if (cursor != null && cursor.moveToFirst()) {
            int usernameColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnUsername());
            if (usernameColumnIndex != -1) {
                edtUsername.setText(cursor.getString(usernameColumnIndex));
            }
            int emailColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnEmail());
            if (emailColumnIndex != -1) {
                edtMail.setText(cursor.getString(emailColumnIndex));
            }
            int phoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnPhone());
            if (phoneColumnIndex != -1) {
                edtPhone.setText(cursor.getString(phoneColumnIndex));
            }
            int dobColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnDob());
            if (dobColumnIndex != -1) {
                edtDOB.setText(cursor.getString(dobColumnIndex));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}
