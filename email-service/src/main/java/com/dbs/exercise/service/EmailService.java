package com.dbs.exercise.service;

import com.dbs.exercise.model.CashBalance;
import com.dbs.exercise.client.BalanceController;
import com.dbs.exercise.model.EmailContent;
import com.dbs.exercise.model.EmailInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	BalanceController balanceController;

	/**
	 * Send emails notifying the account balance (In actual production, print should be replaced by a relevant service call)
	 * @param emailInfo : Contains account id and email id to send the email
	 */
	public void sendEmail(EmailInfo emailInfo) {
		CashBalance cashBalance = balanceController.getBalance(emailInfo.getAccountId());
		EmailContent email = generateEmail(emailInfo, cashBalance);
		System.out.println("Sending email : " + email.toString());
	}

	/**
	 * Generates email content using email details and account balance details
	 * @param emailInfo : email address, account id
	 * @param cashBalance : account id, balance, currency
	 * @return Generated email message content
	 */
	EmailContent generateEmail(EmailInfo emailInfo, CashBalance cashBalance) {
		return new EmailContent("balance-notification@dbs.com", emailInfo.getEmailAddress(),
				cashBalance.getAccountId(), cashBalance.getBalance(), cashBalance.getCurrencyCode());
	}
}
