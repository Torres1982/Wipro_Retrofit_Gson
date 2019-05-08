package com.wipro.wiproretrofitgson;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    final int MAX_NUMBER_OF_POSTS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsNumberEditText = findViewById(R.id.edit_text_id);
        loadDataButton = findViewById(R.id.button_id);
        postsListView = findViewById(R.id.list_view_id);

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
                    // load the posts from the Internet
                    //Toast.makeText(MainActivity.this, "Number of Posts: " + numberOfPosts, Toast.LENGTH_SHORT).show();
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

        //Toast.makeText(MainActivity.this, "FETCHED from INTERNET!!!", Toast.LENGTH_SHORT).show();

        serviceCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> postsList = response.body();
                int numberOfPosts = Integer.parseInt(postsNumberEditText.getText().toString());
                String[] posts = new String[numberOfPosts];

                for (int i = 0; i < MAX_NUMBER_OF_POSTS; i++) {
                    posts[i] = postsList.get(i).getMovieName();
                }

                // Set Adapter for the List View to scroll through the List of fetched Posts
                postsListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, posts));

                Toast.makeText(MainActivity.this, "RESPONSE: " + numberOfPosts, Toast.LENGTH_SHORT).show();
                Log.i("TAG_ON_RESPONSE", postsList.toString());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("TAG_ON_FAILURE", t.getMessage());
            }
        });
    }
}
