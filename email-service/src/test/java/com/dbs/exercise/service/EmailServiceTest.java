package com.dbs.exercise.service;

import com.dbs.exercise.client.BalanceController;
import com.dbs.exercise.model.CashBalance;
import com.dbs.exercise.model.EmailContent;
import com.dbs.exercise.model.EmailInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailService.class)
public class EmailServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmailService emailService;

    @MockBean
    private BalanceController balanceController;

    private EmailInfo emailInfo;
    private CashBalance cashBalance;

    @Before
    public void setUp(){
        emailInfo = new EmailInfo();
        emailInfo.setAccountId("A");
        emailInfo.setEmailAddress("emailAddress@email.com");

        cashBalance = new CashBalance("A", "SGD", 100);
    }

    @Test
    public void sendEmailTest() {
        Mockito.doReturn(cashBalance).when(balanceController).getBalance(emailInfo.getAccountId());

        emailService.sendEmail(emailInfo);
        verify(balanceController,times(1)).getBalance(emailInfo.getAccountId());
    }

    @Test
    public void generateEmailTest() {
        EmailContent result = emailService.generateEmail(emailInfo, cashBalance);
        assertEquals(emailInfo.getEmailAddress(), result.getToAddress());
        assertEquals(cashBalance.getCurrencyCode(), result.getCurrency());
    }
}
