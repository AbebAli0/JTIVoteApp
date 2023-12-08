package com.example.jtivoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class KahimActivity extends AppCompatActivity {

    ImageView listBtn, viewallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahim);

        listBtn = findViewById(R.id.listBtn);
        viewallBtn = findViewById(R.id.viewallBtn);

        viewallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KahimActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KahimActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}