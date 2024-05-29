package com.example.savingsapp.fragments;

// Importing necessary classes for the fragment
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.savingsapp.db.AppDatabase;

// Defining the BaseFragment class which extends Fragment
public class BaseFragment extends Fragment {
    // Declare an instance of AppDatabase
    protected AppDatabase appDatabase;

    // Method to initialize the appDatabase instance
    protected AppDatabase initAppDb(Context context){
        // Get the instance of the AppDatabase using the provided context
        return appDatabase = AppDatabase.getInstance(context);
    }

    // Method to run a task in the background
    protected void runInBackground(Runnable runnable){
        // Start a new thread to run the task
        new Thread(runnable).start();
    }

    // Method to show a short Toast message
    protected void showToast(Context context, String message){
        // Display a short duration Toast with the provided message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
