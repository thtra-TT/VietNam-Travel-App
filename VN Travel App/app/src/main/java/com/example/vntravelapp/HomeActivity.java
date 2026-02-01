package com.example.vntravelapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tvWelcome);

        // Giả sử dữ liệu được truyền từ Login thành công qua Intent
        String email = getIntent().getStringExtra("USER_EMAIL");
        String username = getIntent().getStringExtra("USER_NAME");

        if (username != null && !username.isEmpty()) {
            tvWelcome.setText("Xin chào, " + username + "!");
        } else if (email != null && !email.isEmpty()) {
            // Lấy phần tên trước dấu @ của email để chào cho thân thiện
            String emailName = email.split("@")[0];
            tvWelcome.setText("Xin chào, " + emailName + "!");
        } else {
            tvWelcome.setText("Xin chào, Du khách!");
        }
    }
}