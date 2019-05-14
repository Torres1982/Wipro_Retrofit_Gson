package com.wipro.wiproretrofitgson;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.wipro.wiproretrofitgson.utility.RetrofitUtility;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EditText postsNumberEditText;
    private Button loadDataButton;
    private Button sendPostButton;
    private ListView postsListView;
    private LinearLayout linearLayout;
    private List<Post> postsList;
    private final static int MAX_NUMBER_OF_POSTS = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsNumberEditText = findViewById(R.id.edit_text_id);
        loadDataButton = findViewById(R.id.button_id);
        sendPostButton = findViewById(R.id.button_post_id);
        postsListView = findViewById(R.id.list_view_id);
        linearLayout = findViewById(R.id.linear_layout_id);

        setButtonVisibility(loadDataButton ,false, Color.LTGRAY, Color.GRAY);
        checkEditTextEmptyAddListener();
        loadDataButtonAddListener();
        sendPostButtonAddListener();
    }

    // Add on Click Listener to the Button for fetching data from the Internet
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

    // Add on Click Listener  to the Button for sending the Post Request
    private void sendPostButtonAddListener() {
        sendPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendPostIntent = new Intent(MainActivity.this, SendPostActivity.class);
                startActivity(sendPostIntent);
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
                    setButtonVisibility(loadDataButton,true, Color.RED, Color.WHITE);
                } else {
                    setButtonVisibility(loadDataButton,false, Color.LTGRAY, Color.GRAY);
                }
            }
        });
    }

    // Display the Posts fetched by calling the Retrofit API
    public void fetchPostsFromInternet() {
        Call<List<Post>> serviceCall = RetrofitUtility.getRetrofitServiceCall();

        serviceCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful()) { // onResponse is called even if there is a failure ( status code 404, ...)
                    postsList = response.body();
                    int numberOfPosts = Integer.parseInt(postsNumberEditText.getText().toString());
                    String[] posts = new String[numberOfPosts];

                    if (postsList != null) {
                        for (int i = 0; i < numberOfPosts; i++) {
                            int postId = postsList.get(i).getPostId();
                            String postTitle = postsList.get(i).getPostTitle();
                            posts[i] = "POST ID: " + postId + "\nTITLE:\n" + postTitle;
                        }

                        // Set Adapter for the List View to scroll through the List of fetched Posts
                        postsListView.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_text_color, R.id.list_view_text_color_id, posts));

                        getSinglePostDetails();
                        setActivityView();

                        Toast.makeText(MainActivity.this, "RESPONSE - POSTS NO: " + numberOfPosts, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "RESPONSE - THERE ARE NO POSTS AVAILABLE!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("TAG_ON_RESPONSE_ERROR", "Responded with a Status Code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("TAG_ON_FAILURE_ERROR", t.getMessage());
            }
        });
    }

    // Register a Listener on List View Item click
    private void getSinglePostDetails() {
        postsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int postId = postsList.get(position).getPostId();
                int postUserId = postsList.get(position).getPostUserId();
                String postTitle = postsList.get(position).getPostTitle();
                String postBody = postsList.get(position).getPostBody();

                startActivityWithBundle(postId, postUserId, postTitle, postBody);
            }
        });
    }

    // Start new Activity to display a List View item's details
    private void startActivityWithBundle(int id, int userId, String title, String body) {
        Intent sendIntent = new Intent(MainActivity.this, PostActivity.class);
        sendIntent.putExtra("post_id", id);
        sendIntent.putExtra("post_user_id", userId);
        sendIntent.putExtra("post_title", title);
        sendIntent.putExtra("post_body", body);
        startActivity(sendIntent);
    }

    // Disable/Enable the Button whether the text Field is empty or not
    private void setButtonVisibility(Button button, boolean isEnabled, int backgroundColor, int textColor) {
        button.setEnabled(isEnabled);
        button.setBackgroundColor(backgroundColor);
        button.setTextColor(textColor);
    }

    // Set Up the Activity Views
    private void setActivityView() {
        postsNumberEditText.setVisibility(View.GONE);
        loadDataButton.setVisibility(View.GONE);
        sendPostButton.setVisibility(View.VISIBLE);
        setButtonVisibility(sendPostButton,true, Color.WHITE, Color.RED);
        linearLayout.setBackgroundColor(Color.DKGRAY);
    }
}
