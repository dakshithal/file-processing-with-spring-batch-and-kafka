package com.dbs.exercise.service;

import com.dbs.exercise.client.BalanceController;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(KafkaConsumerService.class)
public class KafkaConsumerServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    KafkaConsumerService kafkaConsumerService;

    @MockBean
    ConsumerFactory<String, String> factory;

    @MockBean
    BalanceController balanceController;

    @MockBean
    private FileProcessorService fileProcessorService;

    private MockConsumer mockConsumer;

    @Before
    public void setUp(){
        mockConsumer = new MockConsumer<String, String>(OffsetResetStrategy.EARLIEST);
        mockConsumer.assign(Arrays.asList(new TopicPartition("transactions", 0)));

        HashMap<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(new TopicPartition("transactions", 0), 0L);
        mockConsumer.updateBeginningOffsets(beginningOffsets);

        mockConsumer.addRecord(new ConsumerRecord<String, String>("transactions", 0, 0L,
                "mykey", "myvalue0"));
    }

    @Test
    public void consumeBatchSuccessNotificationTest() throws Exception{
        Mockito.doReturn(BatchStatus.COMPLETED).when(fileProcessorService).processEmailInfoFile();
        Mockito.doReturn(10L).when(balanceController).getLastReceivedMsgOffset();
        Mockito.doReturn(mockConsumer).when(factory).createConsumer();
        try {
            kafkaConsumerService.consume("Batch Completed");
        } catch (Exception e){}
        verify(balanceController,times(1)).getLastReceivedMsgOffset();
    }

    @Test
    public void consumeOtherNotificationTest() throws  Exception{
        Mockito.doReturn(BatchStatus.COMPLETED).when(fileProcessorService).processEmailInfoFile();
        Mockito.doReturn(10L).when(balanceController).getLastReceivedMsgOffset();
        Mockito.doReturn(mockConsumer).when(factory).createConsumer();
        kafkaConsumerService.consume("Test Notification");
        verify(balanceController,times(0)).getLastReceivedMsgOffset();
    }
}
