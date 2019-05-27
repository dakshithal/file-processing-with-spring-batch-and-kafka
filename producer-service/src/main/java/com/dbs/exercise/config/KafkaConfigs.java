package com.dbs.exercise.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.dbs.exercise.model.CashTransaction;

@Configuration
public class KafkaConfigs {
	/**
	 * Used to configure kafka producer factory which is used by spring to create kafka producer
	 * @return Configured KafkaProducerFactory for cash transactions
	 */
	@Bean
	public ProducerFactory<String, CashTransaction> cashTxProducerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configs);
	}

	/**
	 * Kafka template will be used to publish messages to kafka
	 * @return : Configured kafka template to send cash transactions
	 */
	@Bean
	public KafkaTemplate<String, CashTransaction> kafkaCashTxTemplate() {
		return new KafkaTemplate<>(cashTxProducerFactory());
	}

	/**
	 * Used to configure kafka producer factory which is used by spring to create kafka producer
	 * @return Configured KafkaProducerFactory for notifications
	 */
	@Bean
	public ProducerFactory<String, String> notificationProducerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(configs);
	}

	/**
	 * Kafka template will be used to publish messages to kafka
	 * @return : Configured kafka template to send notifications
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaStringTemplate() {
		return new KafkaTemplate<>(notificationProducerFactory());
	}
}
