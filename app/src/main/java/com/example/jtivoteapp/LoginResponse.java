package com.example.jtivoteapp;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    ModelLogin model;
    private boolean success;
    private int userId;
    private String message;

    public ModelLogin getModel() {
        return model;
    }

    public void setModel(ModelLogin model) {
        this.model = model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponse(ModelLogin model, String message) {
        this.model = model;
        this.message = message;
    }
    public int getUserId() {
        return userId;
    }
    public boolean isSuccess() {
        return success;
    }

}

