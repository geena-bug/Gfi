package com.example.savingsapp; // Package declaration

import android.content.Intent; // Import for Intent class
import android.os.Bundle; // Import for Bundle class
import android.view.View; // Import for View class
import android.widget.Button; // Import for Button widget
import android.widget.EditText; // Import for EditText widget
import android.widget.TextView; // Import for TextView widget

public class RegisterActivity extends BaseActivity implements View.OnClickListener { // Class declaration

    // Declare UI elements
    Button signupButton;
    TextView loginTextView;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText passwordEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate method, called when the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Set the layout for the activity

        initViews(); // Initialize views
        this.initAppDb(); // Initialize the database
    }

    private void initViews() { // Method to initialize views
        signupButton = findViewById(R.id.login_btn); // Initialize the signup button
        signupButton.setOnClickListener(this); // Set click listener for the signup button

        loginTextView = findViewById(R.id.signup_text); // Initialize the login text view
        loginTextView.setOnClickListener(this); // Set click listener for the login text view

        firstNameEditText = findViewById(R.id.first_name_input); // Initialize the first name edit text
        lastNameEditText = findViewById(R.id.last_name_input); // Initialize the last name edit text
        emailEditText = findViewById(R.id.email_input); // Initialize the email edit text
        passwordEditText = findViewById(R.id.password_input); // Initialize the password edit text
    }

    private void handleSignup() { // Method to handle signup logic
        String firstName = firstNameEditText.getText().toString(); // Get the first name from the edit text
        String lastName = lastNameEditText.getText().toString(); // Get the last name from the edit text
        String email = emailEditText.getText().toString(); // Get the email from the edit text
        String password = passwordEditText.getText().toString(); // Get the password from the edit text

        // Validate the user input
        if (firstName.isEmpty()) {
            showToast("First name is required"); // Show a toast if the first name is empty
            return;
        }
        if (lastName.isEmpty()) {
            showToast("Last name is required"); // Show a toast if the last name is empty
            return;
        }
        if (email.isEmpty()) {
            showToast("Email is required"); // Show a toast if the email is empty
            return;
        }
        if (password.isEmpty()) {
            showToast("Password is required"); // Show a toast if the password is empty
            return;
        }

        // Run the database operation in the background
        runInBackground(() -> {
            // Insert the user into the database
            long id = appDatabase.userDao().insert(firstName, lastName, email, password, "");

            // Show a toast message on the UI thread
            runOnUiThread(() -> showToast("User created successfully"));

            // Go to the PhotoActivity
            Intent intent = new Intent(RegisterActivity.this, PhotoActivity.class);
            intent.putExtra("userId", id); // Pass the user ID to the PhotoActivity
            startActivity(intent); // Start the PhotoActivity
        });
    }

    @Override
    public void onClick(View v) { // Method to handle click events
        int id = v.getId(); // Get the ID of the clicked view
        if (id == R.id.login_btn) {
            handleSignup(); // Call handleSignup if the signup button is clicked
        } else if (id == R.id.signup_text) {
            gotoActivity(this, LoginActivity.class); // Go to the LoginActivity if the login text view is clicked
        }
    }
}
