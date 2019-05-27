package com.dbs.exercise.batch;

import com.dbs.exercise.model.CashTransaction;
import com.dbs.exercise.service.KafkaProducerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(CashTransactionWriter.class)
public class CashTransactionWriterTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CashTransactionWriter cashTransactionWriter;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    private CashTransaction cashTransaction;

    @Before
    public void setUp(){
        cashTransaction = new CashTransaction("ACC1", "P", 100, "SGD");
    }

    @Test
    public void writeBatchItemTest() {
        Mockito.doNothing().when(kafkaProducerService).publishCashTransaction(cashTransaction);

        List<CashTransaction> transactions = new ArrayList<>();
        transactions.add(cashTransaction);
        transactions.add(cashTransaction);
        transactions.add(cashTransaction);
        cashTransactionWriter.write(transactions);

        verify(kafkaProducerService,times(3)).publishCashTransaction(cashTransaction);
    }
}
