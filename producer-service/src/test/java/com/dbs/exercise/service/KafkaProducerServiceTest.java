package com.dbs.exercise.service;

import com.dbs.exercise.model.CashTransaction;
import org.apache.kafka.clients.producer.MockProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(KafkaProducerService.class)
public class KafkaProducerServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @MockBean
    private KafkaTemplate<String, CashTransaction> kafkaTransactionTemplate;
    @MockBean
    private KafkaTemplate<String, String> kafkaNotificationTemplate;

    private CashTransaction cashTransaction;

    @Before
    public void setUp() {
        cashTransaction = new CashTransaction("AAA", "P", 100, "SGD");
    }

    public void publishCashTransactionTest() {
        Mockito.doReturn(null).when(kafkaTransactionTemplate.send("test", cashTransaction.getAccountId(),
                cashTransaction));

        kafkaProducerService.publishCashTransaction(cashTransaction);

        verify(kafkaTransactionTemplate,times(1)).send("test", cashTransaction.getAccountId(),
                cashTransaction);
    }

    @Test
    public void publishNotificationTest() {
        MockProducer<String, String> mockProducer = new MockProducer<>();

        Mockito.doReturn(null).when(kafkaNotificationTemplate).send(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());

        kafkaProducerService.publishNotification("a");

        verify(kafkaNotificationTemplate, times(1)).send(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

}
