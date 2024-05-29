package com.example.savingsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StarterActivity extends BaseActivity implements View.OnClickListener {

    // Declare buttons for create account and login
    Button createAccountBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the layout activity_starter
        setContentView(R.layout.activity_starter);
        // Initialize views
        initViews();
        // Initialize database. This will create the database if it does not exist and will also create the tables
        initAppDb();
        // Redirect to login if user exists
        redirectToLoginIfUserExists();
    }

    // Method to initialize views
    private void initViews() {
        // Initialize create account button and set click listener
        createAccountBtn = findViewById(R.id.get_started);
        createAccountBtn.setOnClickListener(this);
        // Initialize login button and set click listener
        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(this);
    }

    // Handle button click events
    @Override
    public void onClick(View v) {
        // Get the ID of the clicked view
        int viewId = v.getId();
        // Check if the create account button was clicked
        if (viewId == R.id.get_started) {
            // Navigate to RegisterActivity
            gotoActivity(this, RegisterActivity.class);
        }
        // Check if the login button was clicked
        else if (viewId == R.id.login) {
            // Navigate to LoginActivity
            gotoActivity(this, LoginActivity.class);
        }
    }

    // Method to redirect to login if a user exists in the database
    void redirectToLoginIfUserExists() {
        // Run the check in the background
        runInBackground(() -> {
            // Check if any users exist in the database
            if (appDatabase.userDao().getUserCount() > 0) {
                // If a user exists, navigate to MainActivity
                gotoActivity(this, MainActivity.class);
            }
        });
    }
}
