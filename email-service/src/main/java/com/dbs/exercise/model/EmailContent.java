package com.dbs.exercise.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmailContent {
    private String fromAddress;
    private String toAddress;
    private String accountId;
    private double balance;
    private String currency;

    private String subject;
    private String body;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public EmailContent(String fromAddress, String toAddress, String accountId, double balance, String currency) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
    }

    private String generateBody(){
        return "The balance of account : " + accountId + " is " +
                new BigDecimal(balance).setScale(2, RoundingMode.HALF_UP).doubleValue() + " " + currency + ".";
    }

    private String generateSubject() {
        return "Balance update for account : " + accountId;
    }

    @Override
    public String toString() {
        return "\n--------------------- Email Content -------------------------\n" +
                "From       : " + fromAddress + "\n" +
                "To         : " + toAddress + "\n" +
                "Subject    : " + generateSubject() + "\n" +
                "Body       : " + generateBody() + "\n" +
                "-------------------------------------------------------------\n";
    }
}
