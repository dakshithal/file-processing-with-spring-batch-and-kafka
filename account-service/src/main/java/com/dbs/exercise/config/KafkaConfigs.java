package com.dbs.exercise.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.dbs.exercise.model.CashTransaction;

@EnableKafka
@Configuration
public class KafkaConfigs {

	/**
	 * Used to configure kafka consumer factory which is used by spring to create kafka consumer
	 * @return : Configured KafkaConsumerFactory
	 */
	@Bean
	public ConsumerFactory<String, CashTransaction> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), 
				new JsonDeserializer<>(CashTransaction.class));
	}

	/**
	 * Used to configure KafkaListenerContainerFactory. It will be used to generate kafka listener
	 * @return : Configured ConcurrentKafkaListenerContainerFactory
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CashTransaction> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CashTransaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
