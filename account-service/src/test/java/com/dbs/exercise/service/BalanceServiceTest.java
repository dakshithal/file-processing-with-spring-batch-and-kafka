package com.dbs.exercise.service;

import com.dbs.exercise.model.CashBalance;
import com.dbs.exercise.model.CashTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(BalanceService.class)
public class BalanceServiceTest {

    @Autowired
    BalanceService balanceService;

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
    public void getBalanceWhenAccountIdExistTest() throws  Exception{
        balanceService.addBalance(cashTransaction);
        CashBalance balance = balanceService.getBalance(cashTransaction.getAccountId());
        assertEquals(cashTransaction.getAccountId(), balance.getAccountId());
        assertEquals(900, balance.getBalance(), 0.0001);
        assertEquals(cashTransaction.getCurrencyCode(), balance.getCurrencyCode());
    }

    @Test(expected = Exception.class)
    public void getBalanceWhenAccountIdNotExistTest() throws  Exception{
        CashBalance balance = balanceService.getBalance("NOTEXIST");
    }

    @Test
    public void getConvertedValueWhenRateExist() {
        assertEquals(10, balanceService.getConvertedValue("LKR", 100),0.0001);
    }

    @Test
    public void getConvertedValueWhenRateNotExist(){
        assertEquals(100, balanceService.getConvertedValue("NON", 100), 0.0001);
    }

    @Test
    public void lastReceivedOffsetTest(){
        balanceService.setLastReceivedMsgOffSet(100L);
        assertEquals(100L, balanceService.getLastReceivedMsgOffSet());
    }
}
