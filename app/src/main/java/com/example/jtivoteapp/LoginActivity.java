package com.example.jtivoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {


    private EditText etnim, etpassword;

    ImageView btnLogin;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etnim = findViewById(R.id.etnim);
        etpassword = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnLogin);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin(){
        String nim = etnim.getText().toString();
        String password = etpassword.getText().toString();
        if (nim.isEmpty() || password.isEmpty()) {
            alertFail("Nim atau Password Kosong!");
        }else {
            sendLogin(nim, password);
        }
    }



    private void sendLogin(String nim, String password) {
        String BASE_URL = ApiClass.Login_API;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginApiService login = retrofit.create(LoginApiService.class);
        Call<LoginResponse> call = login.login(nim, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()) {
                    if (loginResponse != null && loginResponse.getMessage().equals("Login berhasil")) {
                        int userId = loginResponse.getUserId(); // Dapatkan user_id
                        saveLoggedInUser(userId); // Simpan user_id ke SharedPreferences setelah login berhasil
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        alertFail("Nim atau Password Salah");
                    }
                } else {
                    // Tangani respons gagal dari server
                    Toast.makeText(LoginActivity.this, "Gagal melakukan login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void alertFail(String s){
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    // Setelah berhasil login
    private void saveLoggedInUser(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("loggedInUserId", userId);
        editor.apply();
    }
}
