package com.wipro.wiproretrofitgson;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.wipro.wiproretrofitgson.utility.RetrofitUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendPostActivity extends AppCompatActivity {
    private EditText titleEditText, bodyEditText;
    private Button sendPostRequestButton;
    private String title, body;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_post_activity);

        titleEditText = findViewById(R.id.edit_text_title);
        bodyEditText = findViewById(R.id.edit_text_body);
        sendPostRequestButton = findViewById(R.id.button_send_post_request_id);

        sendPostRequestButtonAddListener();
    }

    // Add a Click listener for the Button to send a Post Request
    private void sendPostRequestButtonAddListener() {
        sendPostRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleEditText.getText().toString();
                body = bodyEditText.getText().toString();
                boolean isValid = validateEditTexts(title, body);

                if (isValid) {
                    sendPostRequest();
                } else {
                    Toast.makeText(SendPostActivity.this, "ERROR: Title and Body are MANDATORY fields! Body must be OVER 5 characters!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Send the @POST Request using the Retrofit 2 API
    private void sendPostRequest() {
        final int RANDOM_USER_ID = 5;
        Call<Post> serviceCallPostRequest = RetrofitUtility.getRetrofitServiceCall().createNewPost(RANDOM_USER_ID, title, body);

        serviceCallPostRequest.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int postId = response.body().getPostId(); // automatically injected by the Retrofit API
                    int userId = response.body().getPostUserId();
                    String postTitle = response.body().getPostTitle();
                    String postBody = response.body().getPostBody();

                    Toast.makeText(SendPostActivity.this, "@POST RESPONSE SUCCESS!\nID: " + postId + "\nUSER ID: " + userId + "\nTITLE: " + postTitle + "\nBODY: " + postBody, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendPostActivity.this, "@POST RESPONSE FAILED!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Toast.makeText(SendPostActivity.this, "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Validate the Edit Texts
    private boolean validateEditTexts(String title, String body) {
        return (!title.equals("")) && (!body.equals("")) && ((body.trim()).length() >= 5);
    }
}
