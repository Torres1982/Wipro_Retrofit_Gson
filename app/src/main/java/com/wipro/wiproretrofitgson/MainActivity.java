package com.wipro.wiproretrofitgson;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText postsNumberEditText;
    Button loadDataButton;
    final int MAX_NUMBER_OF_POSTS = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsNumberEditText = findViewById(R.id.edit_text_id);
        loadDataButton = findViewById(R.id.button_id);

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
                    Toast.makeText(MainActivity.this, "Number of Posts: " + numberOfPosts, Toast.LENGTH_SHORT).show();
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
}
