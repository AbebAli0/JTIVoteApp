package com.example.jtivoteapp;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface VoteService {
    @FormUrlEncoded
    @POST("vote.php") // Sesuaikan dengan nama file PHP yang digunakan untuk melakukan vote
    Call<VoteResponse> submitVote(
            @Field("voter_id") int voterId,
            @Field("candidate_id") int candidateId
    );
}