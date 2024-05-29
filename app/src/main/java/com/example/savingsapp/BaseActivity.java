package com.example.savingsapp;

// Importing necessary classes for the application
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.savingsapp.db.AppDatabase;

// Defining the BaseActivity class which extends AppCompatActivity
public class BaseActivity extends AppCompatActivity {

    // Declare an instance of AppDatabase
    AppDatabase appDatabase;

    // Method to initialize the appDatabase instance
    protected AppDatabase initAppDb(){
        // Get the instance of the AppDatabase
        return appDatabase = AppDatabase.getInstance(this);
    }

    // Override the onDestroy method to close the database when the activity is destroyed
    protected void onDestroy(){
        // Close the database instance
        AppDatabase.getInstance(this).close();
        // Call the superclass's onDestroy method
        super.onDestroy();
    }

    // Method to show a short Toast message
    void showToast(String message){
        // Display a short duration Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to show a long Toast message
    void showLongToast(String message){
        // Display a long duration Toast
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Method to navigate to another activity
    void gotoActivity(Context context, Class<?> toClass){
        // Create an Intent to start the specified activity
        Intent intent  =  new Intent(context, toClass);
        // Start the activity
        startActivity(intent);
    }

    // Method to run a task in the background
    void runInBackground(Runnable runnable){
        // Start a new thread to run the task
        new Thread(runnable).start();
    }

    // Method to check if a specific permission is granted
    boolean isPermissionGranted(String permission) {
        // Check if the permission is granted
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
