package controller;

import static android.content.ContentValues.TAG;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import database.DatabaseHelper;
import model.User;

import com.example.hotelbookingapp.R;

public class AccountActivity extends AppCompatActivity {
    TextView txtUsername, txtMail,txtPhone, txtDOB, txtPassword;
    Button btnSave;
    DatabaseHelper databaseHelper;
//    int userId = databaseHelper.getUserId();
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);


//        txtPassword = findViewById(R.id.txtPassword);
        txtUsername = findViewById(R.id.txtUsername);
        txtMail = findViewById(R.id.txtMail);
        txtPhone = findViewById(R.id.txtPhone);
        txtDOB = findViewById(R.id.txtDOB);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);

        loadUserInfo();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code xử lý khi nhấn nút Lưu
            }
        });
    }

    private void loadUserInfo() {

//        String username = getIntent().getStringExtra("username");
//        String email = getIntent().getStringExtra("email");
//        String phone = getIntent().getStringExtra("phone");
//        String dob = getIntent().getStringExtra("dob");
//
//        txtUsername.setText(username);
//        txtMail.setText(email);
//        txtPhone.setText(phone);
//        txtDOB.setText(dob);
        Cursor cursor = databaseHelper.getUserInfoById(userId);
        Log.d(TAG, "loadUserInfo: " + cursor);
        if (cursor != null && cursor.moveToFirst()) {
            int usernameColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnUsername());
            if (usernameColumnIndex != -1) {
                txtUsername.setText(cursor.getString(usernameColumnIndex));
                Log.d(TAG, "loadUserInfo: "+ txtUsername);
            }
            int emailColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnEmail());
            if (emailColumnIndex != -1) {
                txtMail.setText(cursor.getString(emailColumnIndex));
            }
            int phoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnPhone());
            if (phoneColumnIndex != -1) {
                txtPhone.setText(cursor.getString(phoneColumnIndex));
            }
            int dobColumnIndex = cursor.getColumnIndex(DatabaseHelper.getColumnDob());
            if (dobColumnIndex != -1) {
                txtDOB.setText(cursor.getString(dobColumnIndex));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}
