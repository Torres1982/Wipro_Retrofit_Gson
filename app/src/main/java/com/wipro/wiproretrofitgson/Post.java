package com.wipro.wiproretrofitgson;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    private int postId;
    @SerializedName("title")
    private String postTitle;
    @SerializedName("body")
    private String postBody;

    // *********** Create a Constructor ************
    public Post(int postId, String title, String body) {
        this.postId = postId;
        this.postTitle = title;
        this.postBody = body;
    }

    // ************** Getter methods ****************
    public int getPostId() { return postId; }
    public String getPostTitle() { return postTitle; }
    public String getPostBody() { return postBody; }
}
