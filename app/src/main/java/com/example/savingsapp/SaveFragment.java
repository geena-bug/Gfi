package com.example.savingsapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.savingsapp.db.entities.TransactionHistory;
import com.example.savingsapp.fragments.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass for saving transaction data.
 */
public class SaveFragment extends BaseFragment implements View.OnClickListener {
    // Context for accessing resources and database
    Context context;

    // UI components
    Button saveBtn;
    TextInputEditText amountInput;
    TextInputEditText cardNumberInput;
    TextInputEditText nameInput;
    TextInputEditText cvvInput;
    TextInputEditText expiryInput;

    // Default constructor required for fragment subclasses
    public SaveFragment() {
        // Required empty public constructor
    }

    // Static method to create a new instance of SaveFragment with a given context
    public static SaveFragment newInstance(Context context) {
        SaveFragment fragment = new SaveFragment();
        fragment.context = context;
        return fragment;
    }

    // Called when the fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the app database with the given context
        initAppDb(context);
    }

    // Called to create the view hierarchy associated with the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        // Initialize the UI components
        initViews(view);
        return view;
    }

    // Method to initialize the UI components
    void initViews(View view) {
        // Initialize save button and set click listener
        saveBtn = view.findViewById(R.id.save_btn);
        amountInput = view.findViewById(R.id.amount_input);
        cardNumberInput = view.findViewById(R.id.card_number_input);
        nameInput = view.findViewById(R.id.card_name_input);
        cvvInput = view.findViewById(R.id.card_cvv_input);
        expiryInput = view.findViewById(R.id.card_exp_input);
        saveBtn.setOnClickListener(this);
    }

    // Method to save data to the database
    void saveData() {
        // Retrieve input values
        String amount = amountInput.getText().toString();
        String cardNumber = cardNumberInput.getText().toString();
        String name = nameInput.getText().toString();
        String cvv = cvvInput.getText().toString();
        String expiry = expiryInput.getText().toString();

        // Validate input values
        if (amount.isEmpty() || cardNumber.isEmpty() || name.isEmpty() || cvv.isEmpty() || expiry.isEmpty()) {
            showToast(context, "All fields are required");
            return;
        }

        // Validate expiry date
        if (!isValidExpiryDate(expiry)) {
            showToast(context, "Invalid Expiry date");
            return;
        }

        // Get current date
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String description = "Deposit into savings account";

        // Save data to the database in the background
        runInBackground(() -> {
            // Insert transaction into the database
            appDatabase.transactionsDao().insert(1, Double.parseDouble(amount), date, TransactionHistory.TYPE_DEPOSIT, description);
            // Update user balance in the database
            appDatabase.userDao().updateUserBalanceByAmount(1, Double.parseDouble(amount));
            Log.d("SaveFragmentData", "Data saved");
        });

        // Show success message
        showToast(context, "Savings was successful");

        // Clear input fields
        amountInput.setText("");
        cardNumberInput.setText("");
        nameInput.setText("");
        cvvInput.setText("");
        expiryInput.setText("");
    }

    // Handle click events for the save button
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_btn) {
            saveData();
        }
    }

    // Method to validate the expiry date
    private boolean isValidExpiryDate(String expiryDate) {
        // Regex to validate expiry date format (e.g., "MM/YY")
        String expiryPattern = "^(0[1-9]|1[0-2])/(\\d{2})$";
        if (TextUtils.isEmpty(expiryDate) || !Pattern.matches(expiryPattern, expiryDate)) {
            return false;
        }

        // Additional logic to check if the expiry date is in the future
        Matcher matcher = Pattern.compile(expiryPattern).matcher(expiryDate);
        if (matcher.matches()) {
            int month = Integer.parseInt(matcher.group(1));
            int year = Integer.parseInt(matcher.group(2)) + 2000;

            // Get the current month and year
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int currentMonth = calendar.get(java.util.Calendar.MONTH) + 1;
            int currentYear = calendar.get(java.util.Calendar.YEAR);

            // Validate if the expiry date is in the future
            if (year > currentYear || (year == currentYear && month >= currentMonth)) {
                return true;
            }
        }
        return false;
    }
}
