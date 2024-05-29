package com.example.savingsapp; // Package declaration

import android.os.Bundle; // Import for Bundle class
import android.util.Log; // Import for Log class
import android.view.Menu; // Import for Menu class
import android.view.MenuItem; // Import for MenuItem class

import androidx.activity.EdgeToEdge; // Import for EdgeToEdge class
import androidx.annotation.NonNull; // Import for NonNull annotation
import androidx.appcompat.app.AppCompatActivity; // Import for AppCompatActivity class
import androidx.core.graphics.Insets; // Import for Insets class
import androidx.core.view.ViewCompat; // Import for ViewCompat class
import androidx.core.view.WindowInsetsCompat; // Import for WindowInsetsCompat class
import androidx.fragment.app.Fragment; // Import for Fragment class

import com.example.savingsapp.fragments.HomeFragment; // Import for HomeFragment class
import com.google.android.material.bottomnavigation.BottomNavigationView; // Import for BottomNavigationView class
import com.google.android.material.navigation.NavigationBarView; // Import for NavigationBarView class

public class MainActivity extends BaseActivity implements NavigationBarView.OnItemSelectedListener { // Class declaration

    BottomNavigationView bottomNavigationView; // Bottom navigation view

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate method, called when the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for the activity
        initViews(); // Initialize views
        replaceFragment(HomeFragment.newInstance(this)); // Replace fragment with HomeFragment
    }

    void initViews() { // Method to initialize views
        bottomNavigationView = findViewById(R.id.bottom_navigation); // Initialize bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(this); // Set item selected listener for bottom navigation view
    }

    private void replaceFragment(Fragment fragment) { // Method to replace fragment
        getSupportFragmentManager().beginTransaction() // Begin a new fragment transaction
                .setReorderingAllowed(true) // Allow reordering of fragment transactions
                .add(R.id.fragment_container, fragment, null) // Add fragment to container
                .commit(); // Commit the transaction
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Method to create options menu
        getMenuInflater().inflate(R.menu.nav_menu, menu); // Inflate the menu with items from nav_menu
        return true; // Return true to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // Method to handle options item selection
        if (item.getItemId() == R.id.save) { // Check if the save item is selected
            Log.d("MainActivity", "Save clicked"); // Log the save click
            replaceFragment(SaveFragment.newInstance(this)); // Replace fragment with SaveFragment
        }
        return true; // Return true to indicate item selection was handled
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { // Method to handle navigation item selection
        int menuItemId = menuItem.getItemId(); // Get the ID of the selected menu item
        if (menuItemId == R.id.save) { // Check if the save item is selected
            replaceFragment(SaveFragment.newInstance(this)); // Replace fragment with SaveFragment
        } else if (menuItemId == R.id.home) { // Check if the home item is selected
            replaceFragment(HomeFragment.newInstance(this)); // Replace fragment with HomeFragment
        } else if (menuItemId == R.id.withdraw) { // Check if the withdraw item is selected
            replaceFragment(WithdrawalFragment.newInstance(this)); // Replace fragment with WithdrawalFragment
        } else if (menuItemId == R.id.logout) { // Check if the logout item is selected
            finish(); // Finish the activity
            gotoActivity(this, LoginActivity.class); // Go to LoginActivity
        }
        return true; // Return true to indicate item selection was handled
    }
}
