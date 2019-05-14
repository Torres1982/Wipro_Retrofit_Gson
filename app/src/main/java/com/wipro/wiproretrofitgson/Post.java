package com.wipro.wiproretrofitgson;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    private int postId;
    @SerializedName("userId")
    private int postUserId;
    @SerializedName("title")
    private String postTitle;
    @SerializedName("body")
    private String postBody;

    // *********** Create a Constructor ************
    public Post(int postId, int userId, String title, String body) {
        this.postId = postId;
        this.postUserId = userId;
        this.postTitle = title;
        this.postBody = body;
    }

    public Post(int userId, String title, String body) {
        this.postUserId = userId;
        this.postTitle = title;
        this.postBody = body;
    }

    // ************** Getter methods ****************
    public int getPostId() { return postId; }
    public int getPostUserId() { return postUserId; }
    public String getPostTitle() { return postTitle; }
    public String getPostBody() { return postBody; }

    // ************** Setter methods ****************
    public void setPostId(int id) { this.postId = id; }
    public void setPostUserId(int userId) { this.postUserId = userId; }
    public void setPostTitle(String title) { this.postTitle = title; }
    public void setPostBody(String body) { this.postBody = body; }
}
