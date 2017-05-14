package com.secretbiology.arcade.network;

import com.secretbiology.arcade.network.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface RetroServices {

    @GET("users/{uid}.json")
    Call<User> getUserInfo(@Path("uid") String user,
                           @Query("auth") String token);

    @PATCH("users/{uid}.json")
    Call<User> updateUserInfo(@Path("uid") String uid,
                              @Body User user,
                              @Query("auth") String token);
}
