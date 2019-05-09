package com.wipro.wiproretrofitgson;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText postsNumberEditText;
    Button loadDataButton;
    ListView postsListView;
    LinearLayout linearLayout;
    final int MAX_NUMBER_OF_POSTS = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsNumberEditText = findViewById(R.id.edit_text_id);
        loadDataButton = findViewById(R.id.button_id);
        postsListView = findViewById(R.id.list_view_id);
        linearLayout = findViewById(R.id.linear_layout_id);

        setButtonVisibility(false, Color.LTGRAY, Color.GRAY);
        checkEditTextEmptyAddListener();
        loadDataButtonAddListener();
    }

    // Disable the Button when the text Field is empty
    private void setButtonVisibility(boolean isEnabled, int backgroundColor, int textColor) {
        loadDataButton.setEnabled(isEnabled);
        loadDataButton.setBackgroundColor(backgroundColor);
        loadDataButton.setTextColor(textColor);
    }

    // Add on Click Listener to the Button
    private void loadDataButtonAddListener() {
        // Fetch JSON data from the Internet
        loadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfPosts = Integer.parseInt(postsNumberEditText.getText().toString());

                if (numberOfPosts > MAX_NUMBER_OF_POSTS) {
                    Toast.makeText(MainActivity.this, "Number of Posts cannot exceed " + MAX_NUMBER_OF_POSTS, Toast.LENGTH_SHORT).show();
                }
                else {
                    // Load the posts from the Internet
                    fetchPostsFromInternet();
                }
            }
        });
    }

    // Checking if the Edit Text is empty or not
    private void checkEditTextEmptyAddListener() {
        // Add a Listener for the Edit Text
        postsNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!postsNumberEditText.getText().toString().equals("")) {
                    setButtonVisibility(true, Color.RED, Color.WHITE);
                } else {
                    setButtonVisibility(false, Color.LTGRAY, Color.GRAY);
                }
            }
        });
    }

    // Display the Posts fetched by calling the Retrofit API
    public void fetchPostsFromInternet() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(PostInterface.BASE_URL)
                // GsonConverterFactory converts directly json data to object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostInterface serviceInterface = retrofitBuilder.create(PostInterface.class);
        Call<List<Post>> serviceCall = serviceInterface.getPosts();

        serviceCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful()) { // onResponse is called even if there is a failure
                    List<Post> postsList = response.body();
                    int numberOfPosts = Integer.parseInt(postsNumberEditText.getText().toString());
                    String[] posts = new String[numberOfPosts];

                    for (int i = 0; i < numberOfPosts; i++) {
                        int postId = postsList.get(i).getPostId();
                        String postTitle = postsList.get(i).getPostTitle();
                        String postBody = postsList.get(i).getPostBody();
                        posts[i] = "POST ID: " + postId + "\nTITLE:\n" + postTitle + "\nBODY:\n" + postBody;
                    }

                    // Set Adapter for the List View to scroll through the List of fetched Posts
                    postsListView.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_text_color, R.id.list_view_text_color_id, posts));

                    Toast.makeText(MainActivity.this, "RESPONSE - POSTS NO: " + numberOfPosts, Toast.LENGTH_SHORT).show();
                    postsNumberEditText.setEnabled(false);
                    setButtonVisibility(false, Color.LTGRAY, Color.GRAY);
                    linearLayout.setBackgroundColor(Color.DKGRAY);
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Log.i("TAG_ON_RESPONSE_ERROR", response.errorBody().string());
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("TAG_ON_FAILURE", t.getMessage());
            }
        });
    }
}
