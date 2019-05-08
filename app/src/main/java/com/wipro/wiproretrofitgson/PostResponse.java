package com.wipro.wiproretrofitgson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PostResponse implements PostServiceInterface {
    private List<Post> posts;

    // Create a Constructor
    public PostResponse() {
        posts = new ArrayList<Post>();
    }

    @Override
    public Call<PostResponse> getPostsList() {
        return null;
    }

    @Override
    public Call<Post> getPostById(Integer postId) {
        return null;
    }
}
