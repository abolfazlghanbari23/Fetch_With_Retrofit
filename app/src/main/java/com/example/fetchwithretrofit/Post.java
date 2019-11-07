package com.example.fetchwithretrofit;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int userId;
    private Integer id;
    private String title;
    private String body;

    public Post(int userId,int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @SerializedName("body")
    public String getBody() {
        return body;
    }
}
