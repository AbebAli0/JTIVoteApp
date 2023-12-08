package com.example.jtivoteapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VoteService {
    @FormUrlEncoded
    @POST("vote.php")
    Call<VoteResponse> submitVote(
            @Field("voter_id") int voterId,
            @Field("candidate_id") int candidateId
    );
}
