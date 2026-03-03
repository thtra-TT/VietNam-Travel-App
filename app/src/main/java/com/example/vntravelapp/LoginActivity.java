package com.example.vntravelapp;

import android.content.Intent;
import android.content.SharedPreferences; // Phải có import này
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // BƯỚC 1: Lấy dữ liệu người dùng nhập vào trước
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // BƯỚC 2: Kiểm tra xem có để trống không
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // BƯỚC 3: Đọc dữ liệu đã lưu từ SharedPreferences
        SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        // "admin@gmail.com" và "123456" là giá trị mặc định nếu chưa đăng ký gì
        String savedEmail = pref.getString("saved_email", "admin@gmail.com");
        String savedPass = pref.getString("saved_password", "123456");
        String savedName = pref.getString("saved_username", ""); // Lấy tên để chào ở Home

        // BƯỚC 4: So sánh
        if (email.equals(savedEmail) && password.equals(savedPass)) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            // Chuyển sang màn hình Home
            Intent intent = new Intent(this, HomeActivity.class);
            // Truyền dữ liệu sang Home để hiển thị lời chào
            intent.putExtra("USER_EMAIL", email);
            intent.putExtra("USER_NAME", savedName);
            startActivity(intent);
            finish(); // Đóng màn hình Login để không quay lại được bằng nút Back
        } else {
            Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}