package com.example.vntravelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vntravelapp.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        dbHelper = new DatabaseHelper(this);

        // Kiểm tra nếu đã đăng nhập rồi thì vào thẳng Home
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        if (pref.getBoolean("is_logged_in", false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        initViews();

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            handleLogin();
        });
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
    }

    private void handleLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra đăng nhập qua DatabaseHelper
        Cursor cursor = dbHelper.loginUser(email, password);
        
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy thông tin user từ cursor
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
            String userImage = cursor.getString(cursor.getColumnIndexOrThrow("user_image"));
            cursor.close();

            // Lưu trạng thái đăng nhập vào SharedPreferences
            SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("is_logged_in", true);
            editor.putString("saved_email", email);
            editor.putString("saved_username", fullname);
            editor.putString("saved_user_image", userImage);
            editor.apply();

            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("USER_EMAIL", email);
            intent.putExtra("USER_NAME", fullname);
            intent.putExtra("USER_IMAGE", userImage);
            startActivity(intent);
            finish();
        } else {
            if (cursor != null) cursor.close();
            Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}
