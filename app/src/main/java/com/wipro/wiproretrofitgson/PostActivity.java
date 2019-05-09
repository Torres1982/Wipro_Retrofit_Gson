package com.wipro.wiproretrofitgson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        // Receive intent with Bundle for the Post details
        Intent receiveIntent = getIntent();
        Bundle extras = receiveIntent.getExtras();

        if (extras != null) {
            int postId = extras.getInt("post_id");
            String postTitle = extras.getString("post_title");
            String postBody = extras.getString("post_body");

            Toast.makeText(PostActivity.this, "New Activity Started!" + postId, Toast.LENGTH_SHORT).show();
        }
    }
}
