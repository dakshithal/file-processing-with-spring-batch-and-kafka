package com.dbs.exercise.controller;

import com.dbs.exercise.model.CashBalance;
import com.dbs.exercise.service.BalanceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BalanceController.class)
public class BalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    private CashBalance cashBalance;

    @Before
    public void setUp(){
        cashBalance = new CashBalance("AA11","SGD",100);
    }

    @Test
    public void returnBalanceWhenAccountIdExistsTest() throws Exception {
        Mockito.doReturn(cashBalance).when(balanceService).getBalance(cashBalance.getAccountId());

        mockMvc.perform(get("/balance/" + cashBalance.getAccountId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(balanceService,times(1)).getBalance(cashBalance.getAccountId());
    }

    @Test
    public void throwExceptionWhenAccountIdNotExistsTest() throws Exception {
        Mockito.doThrow(Exception.class).when(balanceService).getBalance("ABC");

        mockMvc.perform(get("/balance/ABC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getLastProcessedMsgOffsetTest() throws Exception{
        Mockito.doReturn(100L).when(balanceService).getLastReceivedMsgOffSet();

        mockMvc.perform(get("/balance/lastOffset")
                .accept(MediaType.ALL_VALUE))
                .andExpect(status().isOk());

        verify(balanceService,times(1)).getLastReceivedMsgOffSet();
    }
}
