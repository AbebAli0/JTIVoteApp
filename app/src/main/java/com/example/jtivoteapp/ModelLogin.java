package com.example.jtivoteapp;

public class ModelLogin {
    private String nim;
    private String password;
    private int userId;

    // Constructor
    public ModelLogin(String nim, String password, int userId) {
        this.nim = nim;
        this.password = password;
        this.userId = userId;
    }

    // Getter and setter for nim
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
