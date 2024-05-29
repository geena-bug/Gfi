package com.example.savingsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savingsapp.R;
import com.example.savingsapp.db.entities.TransactionHistory;

import java.util.ArrayList;

public class SavingsActivityAdapter extends RecyclerView.Adapter<SavingsActivityAdapter.SavingsViewHolder> {
    // List of transaction history data
    ArrayList<TransactionHistory> transactionHistoryList;
    // Context for accessing resources
    Context context;

    /**
     * Constructor for initializing the adapter with transaction history data.
     * @param transactionHistoryList List of transaction history data
     */
    public SavingsActivityAdapter(ArrayList<TransactionHistory> transactionHistoryList){
        this.transactionHistoryList = transactionHistoryList;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return A new SavingsViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public SavingsActivityAdapter.SavingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for an individual item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activities_layout, parent, false);
        // Get the context from the parent
        context = parent.getContext();
        // Return a new holder instance
        return new SavingsViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SavingsActivityAdapter.SavingsViewHolder holder, int position) {
        // Get the transaction history item at the specified position
        TransactionHistory transactionHistory = transactionHistoryList.get(position);
        // Set the description text
        holder.description.setText(transactionHistory.description);
        // Set the amount text
        holder.amount.setText(context.getString(R.string.amount, transactionHistory.amount));
        // Set the date text
        holder.dateTextView.setText(context.getString(R.string.transaction_date, transactionHistory.date));

        // Check the type of transaction and update the UI accordingly
        if (TransactionHistory.TYPE_DEPOSIT.equals(transactionHistory.type) || TransactionHistory.TYPE_INTEREST.equals(transactionHistory.type)) {
            // Set the dot image to green for deposit or interest
            holder.dotImage.setImageResource(R.drawable.circle_green);
            // Set the text color to black
            holder.description.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.black));
        } else {
            // Set the dot image to red for other types of transactions
            holder.dotImage.setImageResource(R.drawable.circle_red);
            // Set the text color to red
            holder.description.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        // Return the size of the transaction history list
        return transactionHistoryList.size();
    }

    /**
     * ViewHolder class that describes an item view and metadata about its place within the RecyclerView.
     */
    public static class SavingsViewHolder extends RecyclerView.ViewHolder {
        // ImageView for the dot indicating transaction type
        ImageView dotImage;
        // TextView for the transaction description
        TextView description;
        // TextView for the transaction amount
        TextView amount;
        // TextView for the transaction date
        TextView dateTextView;

        /**
         * Constructor for initializing the ViewHolder with the specified itemView.
         * @param itemView The view that will be used to display the data at the specified position.
         */
        public SavingsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            dotImage = itemView.findViewById(R.id.dot_image);
            description = itemView.findViewById(R.id.description);
            amount = itemView.findViewById(R.id.amount);
            dateTextView = itemView.findViewById(R.id.date_text);
        }
    }
}
