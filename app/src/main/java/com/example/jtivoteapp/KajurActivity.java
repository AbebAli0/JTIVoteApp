package com.example.jtivoteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import android.widget.Toast;
import android.util.Log;


public class KajurActivity extends AppCompatActivity {

    // Deklarasi tombol untuk navigasi
    private ImageView listBtn, viewallBtn;

    // Deklarasi tombol untuk voting kandidat
    private Button voteButton1, voteButton2, voteButton3, voteButton4;

    private VoteService voteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kajur);

        // Inisialisasi elemen UI untuk navigasi
        listBtn = findViewById(R.id.listBtn);
        viewallBtn = findViewById(R.id.viewallBtn);

        // Inisialisasi tombol vote
        voteButton1 = findViewById(R.id.Candidate1Button);
        voteButton2 = findViewById(R.id.Candidate2Button);
        voteButton3 = findViewById(R.id.Candidate3Button);
        voteButton4 = findViewById(R.id.Candidate4Button);

        // Inisialisasi Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.75.81/login/") // Ganti dengan base URL API Anda
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        voteService = retrofit.create(VoteService.class);

        // Aksi ketika tombol "View All" ditekan
        viewallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KajurActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        // Aksi ketika tombol "List" ditekan
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KajurActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Aksi ketika tombol vote untuk setiap kandidat ditekan
        voteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performVote(1); // ID kandidat 1
            }
        });

        voteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performVote(2); // ID kandidat 2
            }
        });

        voteButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performVote(3); // ID kandidat 3
            }
        });

        voteButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performVote(4); // ID kandidat 4
            }
        });

        // Lakukan hal yang serupa untuk tombol vote Kandidat 3 dan Kandidat 4
    }

    private void performVote(int candidateId) {
        int voteId = 1; // Ganti dengan ID voter yang sesuai
        long currentTime = System.currentTimeMillis();

        Vote vote = new Vote();
        vote.setVoteId(voteId);
        vote.setCandidateId(candidateId);
        vote.setStartTime(currentTime);

        Call<Void> call = voteService.submitVote(vote);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Voting berhasil
                    Toast.makeText(KajurActivity.this, "Voting berhasil!", Toast.LENGTH_SHORT).show();
                } else {
                    // Voting gagal, mungkin karena koneksi gagal atau masalah server
                    Toast.makeText(KajurActivity.this, "Gagal melakukan voting. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Gagal melakukan koneksi ke server, tampilkan pesan kesalahan jika diperlukan
                Toast.makeText(KajurActivity.this, "Gagal terhubung ke server!", Toast.LENGTH_SHORT).show();
                // atau tampilkan pesan kesalahan di logcat
                Log.e("Koneksi Gagal", "Gagal terhubung ke server: " + t.getMessage());
            }
        });
    }
}