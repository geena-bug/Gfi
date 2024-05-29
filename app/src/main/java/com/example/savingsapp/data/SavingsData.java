package com.example.savingsapp.data;

public class SavingsData {
    // Private member variables to hold the savings data
    private String description; // Description of the transaction
    private String amount; // Amount of the transaction
    private String date; // Date of the transaction
    private boolean isCredit; // Boolean indicating if the transaction is a credit

    /**
     * Constructor to initialize the savings data.
     * @param description Description of the transaction
     * @param amount Amount of the transaction
     * @param date Date of the transaction
     * @param isCredit Boolean indicating if the transaction is a credit
     */
    public SavingsData(String description, String amount, String date, boolean isCredit) {
        this.description = description; // Initialize description
        this.amount = amount; // Initialize amount
        this.date = date; // Initialize date
        this.isCredit = isCredit; // Initialize isCredit
    }

    /**
     * Getter method for the description.
     * @return Description of the transaction
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter method for the amount.
     * @return Amount of the transaction
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Getter method for the date.
     * @return Date of the transaction
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter method for the isCredit flag.
     * @return Boolean indicating if the transaction is a credit
     */
    public boolean getIsCredit() {
        return isCredit;
    }
}
