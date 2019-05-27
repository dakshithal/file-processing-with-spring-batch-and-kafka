package com.dbs.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dbs.exercise.model.CashTransaction;

@Service
public class KafkaProducerService {
	
	@Autowired
	private KafkaTemplate<String, CashTransaction> kafkaTransactionTemplate;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaNotificationTemplate;
	
	private String transactionsTopic = "transactions";
	private String notificationsTopic = "notifications";

	/**
	 * Publishes cash transaction objects to kafka
	 * @param cashTransaction : The cash transaction to be published
	 */
	public void publishCashTransaction(CashTransaction cashTransaction) {
		//Using account id as key ensures a same partition is used for the same account
		//It make sure the order of transactions are preserved per account
		kafkaTransactionTemplate.send(transactionsTopic, cashTransaction.getAccountId(), cashTransaction);
	}

	/**
	 * Publishes notificatin strings to kafka
	 * @param notification : The notification to be published
	 */
	public void publishNotification(String notification) {
		kafkaNotificationTemplate.send(notificationsTopic, notification, notification);
	}
}
