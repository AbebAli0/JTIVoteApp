package com.example.jtivoteapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
        @Field("nim") String nim,
        @Field("password") String password
        );
}
