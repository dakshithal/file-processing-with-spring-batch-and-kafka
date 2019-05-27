package com.dbs.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.exercise.model.CashBalance;
import com.dbs.exercise.service.BalanceService;

@RestController
@RequestMapping("/balance")
public class BalanceController {
	
	@Autowired
	private BalanceService balanceService;

	/**
	 * Returns CashBalance object for a given account id.
	 * @param accountId : Account id the balance is searched for
	 * @return CashBalance of given account
	 * @throws Exception
	 */
	@RequestMapping("/{accountId}")
	@ResponseStatus(HttpStatus.OK)
	public CashBalance getBalance(@PathVariable("accountId") String accountId) throws Exception {
			return balanceService.getBalance(accountId);
	}

	/**
	 * Used to get the last processed balance message offset from kafka.
	 * This will be used email service to verify all transactions are processed before sending emails
	 * @return The last received cash balance messages's offset from kafka
	 */
	@RequestMapping("/lastOffset")
	@ResponseStatus(HttpStatus.OK)
	public Long getLastReceivedMsgOffset() {
		return balanceService.getLastReceivedMsgOffSet();
	}
}
