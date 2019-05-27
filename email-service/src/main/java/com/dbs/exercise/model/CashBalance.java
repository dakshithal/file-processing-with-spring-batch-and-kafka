package com.dbs.exercise.model;

public class CashBalance {
	private String accountId;
	private String currencyCode;
	private double balance;

	public CashBalance(){
		super();
	}
	
	public CashBalance(String accountId, String currencyCode, double balance) {
		super();
		this.accountId = accountId;
		this.currencyCode = currencyCode;
		this.balance = balance;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
