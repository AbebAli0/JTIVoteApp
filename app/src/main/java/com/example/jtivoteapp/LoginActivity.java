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

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {


    private EditText etnim, etpassword;
    private String nim, password;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etnim = findViewById(R.id.etnim);
        etpassword = findViewById(R.id.etpassword);
        Login = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }


    private void checkLogin(){
        nim = etnim.getText().toString();
        password = etpassword.getText().toString();
        if (nim.isEmpty() || password.isEmpty()) {
            alertFail("Isi NIM atau Password");
        }else {
            sendLogin();
        }
    }



    private void sendLogin() {
        JSONObject params = new JSONObject();
        try {
            params.put("nim", nim);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = params.toString();
        Log.d("TAG", "Data: " + data);
        String url = getString(R.string.api_server) + "/login";
        Log.d("TAG", "URL: " + url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Http http = new Http(LoginActivity.this, url);
                http.setMethod("POST");
                http.setData(data);
                http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Integer code = http.getStatusCode();
                        Log.d("TAG", "Code: " + code);
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                Log.d("TAG", "response: " + response);
                                String token = response.getString("token");
                                Log.d("TAG", "token: " + token);
                                localStorage.setToken(token);
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                Log.d("TAG", "Intent: " + intent);
                                Toast.makeText(LoginActivity.this, "Selamat Datang di Aplikasi JTI Voting", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if (code == 422) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                if(response.has("message")){
                                    String msg = response.getString("message");
                                    alertFail(msg);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Data tidak dapat di proses", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (code == 401) {
                            try {
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg = response.getString("Error");
                                alertFail(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error" + code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void alertFail(String s){
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
