package com.example.savingsapp; // Package declaration

import android.content.Intent; // Import for Intent class
import android.os.Bundle; // Import for Bundle class
import android.view.View; // Import for View class
import android.widget.Button; // Import for Button widget
import android.widget.EditText; // Import for EditText widget
import android.widget.TextView; // Import for TextView widget

import com.example.savingsapp.db.entities.User; // Import for User entity

public class LoginActivity extends BaseActivity implements View.OnClickListener { // Class declaration

    EditText emailEditText; // EditText for email input
    EditText passwordEditText; // EditText for password input
    Button loginButton; // Button for login
    TextView signupTextView; // TextView for signup

    User user; // User object

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate method, called when the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for the activity

        initAppDb(); // Initialize the database
        initViews(); // Initialize views
    }

    private void initViews() { // Method to initialize views
        emailEditText = findViewById(R.id.email_input); // Initialize email EditText
        passwordEditText = findViewById(R.id.password_input); // Initialize password EditText
        loginButton = findViewById(R.id.login_btn); // Initialize login Button
        loginButton.setOnClickListener(this); // Set click listener for login Button
        signupTextView = findViewById(R.id.signup_text); // Initialize signup TextView
        signupTextView.setOnClickListener(this); // Set click listener for signup TextView
    }

    @Override
    public void onClick(View v) { // Method to handle click events
        int viewId = v.getId(); // Get the ID of the clicked view
        if (viewId == R.id.login_btn) { // Check if login button is clicked
            handleLogin(); // Handle login
        } else if (viewId == R.id.signup_text) { // Check if signup text is clicked
            gotoActivity(this, RegisterActivity.class); // Go to RegisterActivity
        }
    }

    private void handleLogin() { // Method to handle login logic
        String email = emailEditText.getText().toString(); // Get email from EditText
        String password = passwordEditText.getText().toString(); // Get password from EditText

        // Validate the user input
        if (email.isEmpty()) { // Check if email is empty
            showToast("Email is required"); // Show a toast message
            return; // Return early
        }
        if (password.isEmpty()) { // Check if password is empty
            showToast("Password is required"); // Show a toast message
            return; // Return early
        }

        // Run the login process in a background thread
        runInBackground(() -> {
            user = appDatabase.userDao().getUserByEmailAndPassword(email, password); // Get user from database
            if (user == null) { // Check if user is null
                runOnUiThread(() -> showToast("Login invalid")); // Show invalid login message on UI thread
            } else {
                runOnUiThread(() -> showToast("Login successful")); // Show successful login message on UI thread
                gotoActivity(LoginActivity.this, MainActivity.class); // Go to MainActivity
            }
        });
    }
}
