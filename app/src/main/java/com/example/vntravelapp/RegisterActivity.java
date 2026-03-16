package com.example.vntravelapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vntravelapp.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    private CheckBox cbPolicy;
    private Button btnRegister;
    private TextView tvLogin;
    private ImageView ivBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);
        initViews();

        // Nút quay lại hoặc text "Đăng nhập"
        ivBack.setOnClickListener(v -> finish());
        tvLogin.setOnClickListener(v -> finish());

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
        tvLogin = findViewById(R.id.tvLogin);
        ivBack = findViewById(R.id.ivBack);
    }

    private void performRegistration() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String pass = edtPassword.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();

        if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
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

        // Kiểm tra email đã tồn tại chưa
        if (dbHelper.checkEmailExists(email)) {
            Toast.makeText(this, "Email này đã được đăng ký. Vui lòng sử dụng email khác.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện đăng ký vào SQLite
        boolean success = dbHelper.registerUser(email, pass, fullName, phone);

        if (success) {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại LoginActivity
        } else {
            Toast.makeText(this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
}
