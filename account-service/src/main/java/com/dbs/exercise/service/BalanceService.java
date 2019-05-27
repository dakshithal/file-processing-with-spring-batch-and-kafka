package com.dbs.exercise.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dbs.exercise.model.CashBalance;
import com.dbs.exercise.model.CashTransaction;

@Service
public class BalanceService {
	private Map<String, Double> balanceBook = new HashMap<>();
	private Map<String, Double> conversionRates = new HashMap<>();

	long lastReceivedMsgOffSet = 0;
	
	public BalanceService() {
		conversionRates.put("USD", 1.1);
		conversionRates.put("LKR", 0.1);
		conversionRates.put("AUD", 1.0);
		conversionRates.put("INR", 0.6);
		conversionRates.put("MYR", 0.75);
	}

	/**
	 * Update account balance based on received cash transaction
	 * @param transaction : CashTransaction containing balance update infomation
	 */
	public void addBalance(CashTransaction transaction) {
		String accountId = transaction.getAccountId();
		double amount = transaction.getAmount();
		String currency = transaction.getCurrencyCode();
		String payOrRecieve = transaction.getPayOrRecieve();
		
		double convertedValue = 0.0;
		if (currency.equals("SGD")) {
			convertedValue = amount;
		} else {
			convertedValue = getConvertedValue(currency, amount);
		}
		
		double changedValue = 0.0;
		if (payOrRecieve.equals("R")) {
			changedValue = convertedValue;
		} else {
			changedValue = (-1)*convertedValue;
		}
		
		if (balanceBook.containsKey(accountId)) {
			double newBalance = balanceBook.get(accountId) + changedValue;
			balanceBook.put(accountId, newBalance);
		} else {
			balanceBook.put(accountId, 1000.0 + changedValue);
		}
	}

	/**
	 * Returns the balance details for a given account id
	 * @param accountId : Cash Account Id to find balance details
	 * @return CashBalance details of given account id
	 * @throws Exception : When the given account id does not exist
	 */
	public CashBalance getBalance(String accountId) throws Exception{

		if (balanceBook.containsKey(accountId)) {
			return new CashBalance(accountId, "SGD", balanceBook.get(accountId));
		} else {
			throw new Exception("No such account");
		}
	}

	/**
	 * Converts given value to SGD from the given Currency
	 * @param fromCurrency : From currency to convert to SGD
	 * @param value : Converted value
	 * @return The converted currency value
	 */
	double getConvertedValue(String fromCurrency, double value) {
		if (conversionRates.containsKey(fromCurrency)) {
			return (value * conversionRates.get(fromCurrency));
		} else {
			return value;
		}
	}

	public long getLastReceivedMsgOffSet() {
		return lastReceivedMsgOffSet;
	}

	public void setLastReceivedMsgOffSet(long lastReceivedMsgOffSet) {
		this.lastReceivedMsgOffSet = lastReceivedMsgOffSet;
	}
}
