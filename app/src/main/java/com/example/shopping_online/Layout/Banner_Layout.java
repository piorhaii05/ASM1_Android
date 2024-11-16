package com.example.shopping_online.Layout;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopping_online.R;

public class Banner_Layout extends AppCompatActivity {

    private Button btn_started;
    private TextView txt_goto_login, txt_goto_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banner_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_started = findViewById(R.id.btn_started);
        txt_goto_login = findViewById(R.id.txt_goto_login);
        txt_goto_register = findViewById(R.id.txt_goto_register);

        btn_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Banner_Layout.this, Login_Layout.class));
            }
        });
        txt_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Banner_Layout.this, Login_Layout.class));
            }
        });
        txt_goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Banner_Layout.this, Register_Layout.class));
            }
        });

    }
}