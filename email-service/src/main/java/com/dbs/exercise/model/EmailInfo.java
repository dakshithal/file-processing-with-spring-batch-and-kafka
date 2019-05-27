package com.dbs.exercise.model;

public class EmailInfo {
    private String accountId;
    private String emailAddress;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "EmailInfo{" +
                "accountId='" + accountId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
