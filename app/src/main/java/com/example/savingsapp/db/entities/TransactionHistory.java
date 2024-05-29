package com.example.savingsapp.db.entities;

// Importing necessary Room persistence library classes
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Importing the Date class (though not used in this code, it might be relevant for future use)
import java.util.Date;

// Annotate the class to be an Entity for the Room database with the table name "transaction_history"
@Entity(tableName = "transaction_history")
public class TransactionHistory {
    // Define constants for transaction types
    public static final String TYPE_DEPOSIT = "deposit";
    public static final String TYPE_WITHDRAW = "withdraw";
    public static final String TYPE_INTEREST = "interest";

    // Define the primary key for the table with auto-increment
    @PrimaryKey(autoGenerate = true)
    public int id;

    // Annotate a column with the name "user_id" to store user ID
    @ColumnInfo(name = "user_id")
    public int userId;

    // Annotate a column with the name "amount" to store transaction amount
    @ColumnInfo(name = "amount")
    public double amount;

    // Annotate a column with the name "date" to store transaction date as a String
    @ColumnInfo(name = "date")
    public String date;

    // Annotate a column with the name "type" to store the type of transaction
    @ColumnInfo(name = "type")
    public String type;

    // Annotate a column with the name "description" to store transaction description
    @ColumnInfo(name = "description")
    public String description;
}
