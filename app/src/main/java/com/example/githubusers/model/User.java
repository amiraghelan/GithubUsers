package com.example.githubusers.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    private String userName;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("id")
    private int id;
    @SerializedName("followers")
    private int followers;

    @SerializedName("following")
    private int following;

    @SerializedName("email")
    private String email = "Email Not Available";

    public User(String userName, String avatarUrl, int id, int followers, int following, String email) {
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.id = id;
        this.followers = followers;
        this.following = following;
        if (email !=null) this.email = email;
    }

    public User(String userName, String avatarUrl, int id) {
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.id = id;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getEmail() {
        return email;
    }



    public String getUserName() {
        return userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getId(){
        return id;
    }
}
