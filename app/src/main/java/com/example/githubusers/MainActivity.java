package com.example.githubusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.githubusers.adapter.UsersRecyclerViewAdapter;
import com.example.githubusers.controller.GithubApi;
import com.example.githubusers.controller.GithubApiController;
import com.example.githubusers.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public class MainActivity extends AppCompatActivity {
    Integer since = 0;
    List<User> users;
    UsersRecyclerViewAdapter usersRecyclerViewAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        users = new ArrayList<>();
        Log.d("tag", "onCreate: main activity");

        usersRecyclerViewAdapter = new UsersRecyclerViewAdapter(MainActivity.this, users);
        recyclerView = findViewById(R.id.rcl_users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(linearLayoutManager.findLastVisibleItemPosition() == users.size()-1){
                    fetchUsers();
                }
            }
        });
        recyclerView.setAdapter(usersRecyclerViewAdapter);
        fetchUsers();
    }

    public void fetchUsers(){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = GithubApiController.getRetrofitInstance();
        GithubApi githubApi = retrofit.create(GithubApi.class);

        Call<List<User>> call = githubApi.getUsers(since);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){


                    users.addAll(response.body());

                    int lastId = users.get(users.size()-1).getId();
                    since = lastId>since? lastId : since;
                    usersRecyclerViewAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Failed loading users",Toast.LENGTH_LONG).show();
            }
        });

    }

}