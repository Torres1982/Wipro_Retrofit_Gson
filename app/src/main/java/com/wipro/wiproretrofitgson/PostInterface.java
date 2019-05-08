package com.wipro.wiproretrofitgson;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<Post>> getPosts();
}
