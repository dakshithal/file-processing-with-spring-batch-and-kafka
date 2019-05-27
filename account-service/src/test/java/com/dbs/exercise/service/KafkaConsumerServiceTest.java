package com.dbs.exercise.service;

import com.dbs.exercise.model.CashTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(KafkaConsumerService.class)
public class KafkaConsumerServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    @Autowired
    KafkaConsumerService kafkaConsumerService;

    private CashTransaction cashTransaction;

    @Before
    public void Setup() {
        cashTransaction = new CashTransaction();
        cashTransaction.setAccountId("AAA");
        cashTransaction.setAmount(100);
        cashTransaction.setPayOrRecieve("P");
        cashTransaction.setCurrencyCode("SGD");
    }

    @Test
    public void consumedMsgAddedToBalanceTest() {
        Mockito.doNothing().when(balanceService).addBalance(cashTransaction);
        Mockito.doNothing().when(balanceService).setLastReceivedMsgOffSet(Mockito.anyLong());
        kafkaConsumerService.consume(cashTransaction, 10);
        verify(balanceService,times(1)).addBalance(cashTransaction);
        verify(balanceService,times(1)).setLastReceivedMsgOffSet(Mockito.anyLong());
    }
}
