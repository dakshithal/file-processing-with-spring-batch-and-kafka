package com.dbs.exercise.service;

import com.dbs.exercise.client.BalanceController;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class KafkaConsumerService {
	
	@Autowired
	FileProcessorService fileProcessorService;

	@Autowired
	ConsumerFactory<String, String> factory;

	@Autowired
	BalanceController balanceController;

	/**
	 * Listens to the transactions file batch done notification and triggers email sending start
	 * @param notification : Batch status notification from Producer service
	 * @throws InterruptedException
	 */
	@KafkaListener(topics = "notifications", group = "group_id")
	public void consume(@Payload String notification) throws InterruptedException {

		System.out.println("Notification received : " + notification);
		
		if (notification.equals("Batch Completed")) {
			long currentOffset = balanceController.getLastReceivedMsgOffset();
			long endOffset = this.getEndOffSet();

			System.out.println("current offset : " + currentOffset + " end : " + endOffset);

			//Wait till all the transactions are consumed by the account service
			while (endOffset>currentOffset) {
				Thread.sleep(1000);
				currentOffset = balanceController.getLastReceivedMsgOffset();
				System.out.println("current offset : " + currentOffset + " end : " + endOffset);
			}
			fileProcessorService.processEmailInfoFile();
		}
	}

	/**
	 * Gets the end message offset of cash transactions topic
	 * @return The end message offset of cash transactions topic
	 */
	public long getEndOffSet(){
		Consumer consumer = factory.createConsumer();
		String topic = "transactions";
		TopicPartition topicPartition = new TopicPartition(topic, 0);
		List<TopicPartition> topics = Arrays.asList(new TopicPartition(topic, 0));
		consumer.assign(topics);
		consumer.seekToEnd(topics);
		long endOffSet = consumer.position(topicPartition)-1;
		return endOffSet;
	}
}
