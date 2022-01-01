package com.example.githubusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubusers.controller.GithubApi;
import com.example.githubusers.controller.GithubApiController;
import com.example.githubusers.model.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileActivity extends AppCompatActivity {

    TextView tvUserName;
    TextView tvEmail;
    TextView tvFollowers;
    ImageView imgAvatar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvUserName = findViewById(R.id.tv_userName);
        tvEmail = findViewById(R.id.tv_email);
        tvFollowers = findViewById(R.id.tv_followers);
        imgAvatar = findViewById(R.id.img_avatar);
        progressBar = findViewById(R.id.progressBar);


        int id = getIntent().getIntExtra("id",0);

        Retrofit retrofit = GithubApiController.getRetrofitInstance();
        GithubApi githubApi = retrofit.create(GithubApi.class);
        Call<User> call = githubApi.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    setUpUi(user.getUserName(), user.getEmail(), user.getFollowers(), user.getFollowing(), user.getAvatarUrl());
                }else{
                    userLoadFailed();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("loadUser", "onFailure: loafing user failed: " + t.getMessage());
                userLoadFailed();
            }
        });


    }

    public void setUpUi(String userName, String email, int followers, int following, String avatarUrl){
        tvUserName.setText(userName);
        tvEmail.setText(email);
        tvFollowers.setText(String.format(String.format("Followers: %d Following: %d",followers, following )));
        Picasso.get().load(avatarUrl).placeholder(R.drawable.default_avatar).into(imgAvatar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    public void userLoadFailed(){
        Toast.makeText(this, "Loading the user Failed", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
    }
}