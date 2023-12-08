package com.example.jtivoteapp;

// Contoh model untuk respons dari server
public class VoteResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
