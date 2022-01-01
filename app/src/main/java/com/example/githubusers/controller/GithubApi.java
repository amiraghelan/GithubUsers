package com.example.githubusers.controller;

import com.example.githubusers.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("users")
    Call<List<User>> getUsers(@Query("since") Integer since);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") int id);
}
