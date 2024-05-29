package com.example.savingsapp.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.savingsapp.db.entities.TransactionHistory;

import java.util.List;

@Dao
public interface TransactionsDao {
    /**
     * Inserts a new transaction into the transaction_history table.
     * @param userId The ID of the user who made the transaction
     * @param amount The amount of the transaction
     * @param date The date of the transaction
     * @param type The type of the transaction (e.g., deposit, withdrawal)
     * @param description The description of the transaction
     * @return The row ID of the newly inserted transaction
     */
    @Query("INSERT INTO transaction_history (user_id, amount, date, type, description) VALUES (:userId, :amount, :date, :type, :description)")
    long insert(int userId, double amount, String date, String type, String description);

    /**
     * Inserts a TransactionHistory object into the transaction_history table.
     * @param transactionHistory The TransactionHistory object to be inserted
     * @return The row ID of the newly inserted transaction
     */
    @Insert
    long insertObject(TransactionHistory transactionHistory);

    /**
     * Retrieves all transactions from the transaction_history table.
     * @return A list of all TransactionHistory objects
     */
    @Query("SELECT * FROM transaction_history")
    List<TransactionHistory> getAll();

    /**
     * Retrieves the total number of transactions of a specific type from the transaction_history table.
     * @param type The type of the transactions to count
     * @return The total number of transactions of the specified type
     */
    @Query("SELECT COUNT(id) FROM transaction_history WHERE type = :type")
    int getTotalNoOfTransactionsByType(String type);
}
