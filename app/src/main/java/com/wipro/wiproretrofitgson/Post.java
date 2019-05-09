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

    // ************** Getter methods ****************
    int getPostId() { return postId; }
    int getPostUserId() { return postUserId; }
    String getPostTitle() { return postTitle; }
    String getPostBody() { return postBody; }
}
