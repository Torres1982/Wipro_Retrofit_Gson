package com.wipro.wiproretrofitgson;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int userId;

    @SerializedName("id")
    private int postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("body")
    private String postBody;

    // *********** Create a Constructor ************
    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.postId = id;
        this.postTitle = title;
        this.postBody = body;
    }

    // ************** Getter methods ****************
    public int getUserId() {
        return userId;
    }
    public int getPostId() {
        return postId;
    }
    public String getPostTitle() {
        return postTitle;
    }
    public String getPostBody() {
        return postBody;
    }

    // ************** Setter methods ****************
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
