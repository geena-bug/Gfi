package com.example.savingsapp;

// Importing necessary classes for the activity
import android.os.Bundle;
import android.widget.TextView;

// Defining the HomeActivity class which extends BaseActivity
public class HomeActivity extends BaseActivity {
    // Declare a TextView to display a hello message
    TextView helloMessage;

    // Override the onCreate method to set up the activity when it's created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass's onCreate method
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_home layout
        setContentView(R.layout.activity_home);
        // Initialize the views
        initViews();

        // Set the text of the helloMessage TextView
        helloMessage.setText(getString(R.string.hello_message, "John Doe."));
    }

    // Method to initialize the views
    void initViews(){
        // Find the TextView by its ID and assign it to helloMessage
        helloMessage = findViewById(R.id.hello_message);
    }
}
