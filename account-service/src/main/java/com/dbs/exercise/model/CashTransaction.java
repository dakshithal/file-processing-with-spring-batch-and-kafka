package com.dbs.exercise.model;

public class CashTransaction {
	private String accountId;
	private String payOrRecieve;
	private double amount;
	private String currencyCode;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPayOrRecieve() {
		return payOrRecieve;
	}
	public void setPayOrRecieve(String payOrRecieve) {
		this.payOrRecieve = payOrRecieve;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "CashTransaction [accountId=" + accountId + ", payOrRecieve=" + payOrRecieve + ", amount=" + amount
				+ ", currencyCode=" + currencyCode + "]";
	}
}
