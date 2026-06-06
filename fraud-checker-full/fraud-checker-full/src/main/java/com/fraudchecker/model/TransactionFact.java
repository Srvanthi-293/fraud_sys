package com.fraudchecker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Drools working fact inserted into the rule session.
 * Rules read the fields below and update score + reasons.
 *
 * Plain POJO — no Lombok so Drools can reliably resolve getters/setters.
 */
public class TransactionFact {

    private double amount;
    private boolean newPayee;
    private int transactionHour;
    private boolean locationMismatch;
    private double customerAvgAmount;   // used by Spending Baseline bonus rule

    // Drools rules write to these two
    private int score = 0;
    private List<String> reasons = new ArrayList<>();

    // ── Constructor
    public TransactionFact(double amount, boolean newPayee, int transactionHour,
                           boolean locationMismatch, double customerAvgAmount) {
        this.amount = amount;
        this.newPayee = newPayee;
        this.transactionHour = transactionHour;
        this.locationMismatch = locationMismatch;
        this.customerAvgAmount = customerAvgAmount;
    }

    // ── Getters & Setters
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public boolean isNewPayee() { return newPayee; }
    public void setNewPayee(boolean newPayee) { this.newPayee = newPayee; }

    public int getTransactionHour() { return transactionHour; }
    public void setTransactionHour(int transactionHour) { this.transactionHour = transactionHour; }

    public boolean isLocationMismatch() { return locationMismatch; }
    public void setLocationMismatch(boolean locationMismatch) { this.locationMismatch = locationMismatch; }

    public double getCustomerAvgAmount() { return customerAvgAmount; }
    public void setCustomerAvgAmount(double customerAvgAmount) { this.customerAvgAmount = customerAvgAmount; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
}
