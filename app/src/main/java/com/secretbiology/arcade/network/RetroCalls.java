package com.secretbiology.arcade.network;

import com.secretbiology.arcade.network.models.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroCalls {

    private static final String BASE_URL = "https://secret-games.firebaseio.com/";

    private Retrofit builder() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpClient.addInterceptor(interceptor).build();
    }

    public Call<User> getUserInfo(String uid, String token) {
        return builder().create(RetroServices.class).
                getUserInfo(uid, token);
    }

    public Call<User> updateUserInfo(String uid, User user, String token) {
        return builder().create(RetroServices.class).
                updateUserInfo(uid, user, token);
    }
}
