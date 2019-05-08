package com.wipro.wiproretrofitgson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostServiceInterface {
    @GET("posts")
    Call<PostResponse> getPostsList();

    @GET("posts")
    Call<Post> getPostById(@Query("id") Integer postId);
}
