package com.example.jtivoteapp;

public class ModelLogin {
    String nim,password;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ModelLogin(String nim, String password) {
        this.nim = nim;
        this.password = password;
    }
}
