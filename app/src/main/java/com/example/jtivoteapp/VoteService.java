package com.example.jtivoteapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VoteService {
    @POST("vote.php") // Replace with the appropriate endpoint
    Call<Void> submitVote(@Body Vote vote);
}
