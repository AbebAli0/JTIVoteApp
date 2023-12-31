package com.example.jtivoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DashboardActivity extends AppCompatActivity {

    ImageView kahimBtn, kahmjtiBtn, kajurBtn, listBtn;

    @Override
    public void onBackPressed() {
        // Tidak melakukan apa-apa saat tombol back ditekan
        // Hapus super.onBackPressed(); jika ingin memblock tombol back
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



//        kahimBtn = findViewById(R.id.kahimBtn);
//        kahmjtiBtn = findViewById(R.id.kahmtjiBtn);
        kajurBtn = findViewById(R.id.kajurBtn);
        listBtn = findViewById(R.id.listBtn);


//        kahimBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, KahimActivity.class);
//                startActivity(intent);
//            }
//        });
//        kahmjtiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, KahmjtiActivity.class);
//                startActivity(intent);
//            }
//        });
        kajurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, KajurActivity.class);
                startActivity(intent);
            }
        });
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}