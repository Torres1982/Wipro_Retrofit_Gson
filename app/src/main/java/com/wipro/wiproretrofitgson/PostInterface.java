package com.wipro.wiproretrofitgson;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostInterface {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("posts")
    @FormUrlEncoded
    Call<List<Post>> createNewPost(@Body Post post);
}
