package com.dbs.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.dbs.exercise.model.CashTransaction;

@Service
public class KafkaConsumerService {
	
	@Autowired
	BalanceService balanceService;

	/**
	 * Call back methoed being called when a cash transaction is received at kafka topic
	 * @param cashTransaction : Cash transaction from kafka to be processed
	 * @param offSet : Offset of current message in topic
	 */
	@KafkaListener(topics = "transactions", group = "group_id")
	public void consume(@Payload CashTransaction cashTransaction, @Header(KafkaHeaders.OFFSET) int offSet) {
		System.out.println("Cash transaction received : " + cashTransaction.toString());
		balanceService.addBalance(cashTransaction);
		balanceService.setLastReceivedMsgOffSet(offSet);
	}
}
