package com.example.jtivoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText etnim, etpassword;
    private String nim,password;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etnim = findViewById(R.id.etnim);
        etpassword = findViewById(R.id.etpassword);
        Login = findViewById(R.id.buttonLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingLogin();
            }
        });
    }
    private void checkingLogin(){
        nim = etnim.getText().toString();
        password = etpassword.getText().toString();

        if (nim.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Nim atau Password tidak boleh kosong", Toast.LENGTH_LONG);
        }else{
            sendLogin();
        }
    }

    private void sendLogin(){
        String baseURL = getString(R.string.api_server) + "login.php/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginApiService api = retrofit.create(LoginApiService.class);
        Call<LoginResponse> login = api.login(nim, password);

        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    String message = "Login Berhasil";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (message.equals("Login Berhasil")){
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}