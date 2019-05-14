package com.wipro.wiproretrofitgson.utility;

import com.wipro.wiproretrofitgson.Post;
import com.wipro.wiproretrofitgson.PostInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtility {
    // Create a Retrofit Builder
    private static Retrofit getRetrofitClient() {
        return new Retrofit.Builder()
                .baseUrl(PostInterface.BASE_URL)
                // GsonConverterFactory converts directly JSON data to an Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Create a Service Call
    public static Call<List<Post>> getRetrofitServiceCall() {
        return getRetrofitClient().create(PostInterface.class).getPosts();
    }
}
