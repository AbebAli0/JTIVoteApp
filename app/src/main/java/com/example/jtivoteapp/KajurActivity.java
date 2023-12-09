package com.example.jtivoteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KajurActivity extends AppCompatActivity {

    private VoteService voteService;
    ImageView listBtn, viewallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kajur);

        listBtn = findViewById(R.id.listBtn);
        viewallBtn = findViewById(R.id.viewallBtn);

        viewallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KajurActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KajurActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4/loginjti/") // Ganti dengan base URL API Anda
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        voteService = retrofit.create(VoteService.class);
    }

    public void onVoteClick(View view) {
        // Mendapatkan candidateId berdasarkan tombol yang diklik
        int candidateId = getCandidateId(view.getId());

        // Menampilkan dialog konfirmasi
        showConfirmationDialog(candidateId);
    }

    private void showConfirmationDialog(final int candidateId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah Anda yakin memilih kandidat ini?");

        // Tombol positif (Ya)
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Jika pengguna memilih Ya, lakukan pemilihan
                performVote(candidateId); // Memanggil method performVote dengan candidateId
            }
        });

        // Tombol negatif (Batal)
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Jika pengguna memilih Batal, tutup dialog
                dialog.dismiss();
            }
        });

        // Menampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getCandidateId(int viewId) {
        // Mendapatkan candidateId berdasarkan viewId
        switch (viewId) {
            case R.id.Candidate1Button:
                return 1;
            case R.id.Candidate2Button:
                return 2;
            case R.id.Candidate3Button:
                return 3;
            case R.id.Candidate4Button:
                return 4;
            default:
                return 0;
        }
    }

    private void performVote(int candidateId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int loggedInUserId = sharedPreferences.getInt("loggedInUserId", -1);

        if (loggedInUserId != -1) {
            Call<VoteResponse> call = voteService.submitVote(loggedInUserId, candidateId);
            call.enqueue(new Callback<VoteResponse>() {
                @Override
                public void onResponse(Call<VoteResponse> call, Response<VoteResponse> response) {
                    if (response.isSuccessful()) {
                        VoteResponse voteResponse = response.body();
                        if (voteResponse != null && voteResponse.isSuccess()) {
                            // Sukses
                            Toast.makeText(KajurActivity.this, "Vote successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Gagal, tampilkan pesan kesalahan
                            Toast.makeText(KajurActivity.this, "Vote failed: " + voteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Gagal, tampilkan pesan kesalahan
                        Toast.makeText(KajurActivity.this, "Failed to submit vote.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VoteResponse> call, Throwable t) {
                    // Gagal melakukan koneksi ke server
                    Toast.makeText(KajurActivity.this, "Failed to connect to the server!", Toast.LENGTH_SHORT).show();
                    Log.e("Connection Failed", "Failed to connect to the server: " + t.getMessage());
                }
            });
        } else {
            // User belum login, tampilkan pesan kesalahan atau lakukan sesuatu
            Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show();
        }
    }
}
