package com.example.vntravelapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    private CheckBox cbPolicy;
    private Button btnRegister;
    private TextView tvToLogin;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        // Nút quay lại hoặc text "Đăng nhập"
        btnBack.setOnClickListener(v -> finish());
        tvToLogin.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            performRegistration();
        });
    }

    private void initViews() {
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        cbPolicy = findViewById(R.id.cbPolicy);
        btnRegister = findViewById(R.id.btnRegister);
        tvToLogin = findViewById(R.id.tvToLogin);
        btnBack = findViewById(R.id.btnBack);
    }

    private void performRegistration() {
        // Bước 1: Lấy dữ liệu từ giao diện vào biến String
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String pass = edtPassword.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();

        // Bước 2: Kiểm tra tính hợp lệ (Validation)
        if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập các trường bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbPolicy.isChecked()) {
            Toast.makeText(this, "Bạn phải đồng ý với điều khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirmPass)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Bước 3: Lưu dữ liệu (Chỉ lưu khi mọi thứ đã hợp lệ)
        // Lưu ý: Cần import android.content.SharedPreferences;
        android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putString("saved_email", email);
        editor.putString("saved_password", pass);
        editor.putString("saved_username", fullName);
        editor.apply();

        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        finish(); // Quay lại LoginActivity
    }
}