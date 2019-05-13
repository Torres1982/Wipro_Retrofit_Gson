package com.wipro.wiproretrofitgson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {
    private TextView textViewPostId, textViewUserId, textViewPostTitle, textViewPostBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        textViewPostId = findViewById(R.id.text_view_post_id);
        textViewUserId = findViewById(R.id.text_view_user_id);
        textViewPostTitle = findViewById(R.id.text_view_post_title);
        textViewPostBody = findViewById(R.id.text_view_post_body);

        // Receive intent with Bundle for the Post details
        Intent receiveIntent = getIntent();
        Bundle extras = receiveIntent.getExtras();

        if (extras != null) {
            int postId = extras.getInt("post_id");
            int userId = extras.getInt("post_user_id");
            String postTitle = extras.getString("post_title");
            String postBody = extras.getString("post_body");
            String convertedPostId = String.valueOf(postId);
            String convertedUserId = String.valueOf(userId);

            concatenateTextViewStrings(convertedPostId, convertedUserId, postTitle, postBody);
        }
    }

    // Concatenate Strings for the Text Views
    private void concatenateTextViewStrings(String id, String userId, String title, String body) {
        String finalPostId = "POST ID: " + id;
        String finalUserId = "USER ID: " + userId;
        String finalTitle = "TITLE:\n" + title;
        String finalBody = "BODY:\n" + body;

        populateTextViewPostDetails(finalPostId, finalUserId, finalTitle, finalBody);
    }

    // Populate Text Views with Post details passed through the Intent
    private void populateTextViewPostDetails(String id, String userId, String title, String body) {
        textViewPostId.setText(id);
        textViewUserId.setText(userId);
        textViewPostTitle.setText(title);
        textViewPostBody.setText(body);
    }
}
