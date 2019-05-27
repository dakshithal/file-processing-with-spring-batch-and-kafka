package com.dbs.exercise.batch;

import com.dbs.exercise.model.CashTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(CashTransactionProcessor.class)
public class CashTransactionProcessorTest {
    @Autowired
    CashTransactionProcessor cashTransactionProcessor;

    private CashTransaction cashTransaction;

    @Before
    public void setUp(){
        cashTransaction = new CashTransaction("ACC1", "P", 100,
                "SGD");
    }

    @Test
    public void processBatchItemTest() throws Exception {
        CashTransaction result = cashTransactionProcessor.process(cashTransaction);
        assertEquals(cashTransaction.getAccountId(), result.getAccountId());
        assertEquals(cashTransaction.getCurrencyCode(), result.getCurrencyCode());
    }
}
