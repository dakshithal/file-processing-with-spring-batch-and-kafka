package com.dbs.exercise.batch;

import com.dbs.exercise.model.EmailInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailInfoProcessor.class)
public class EmailInfoProcessorTest {

    @Autowired
    EmailInfoProcessor emailInfoProcessor;

    private EmailInfo emailInfo;

    @Before
    public void setUp(){
        emailInfo = new EmailInfo();
        emailInfo.setAccountId("A");
        emailInfo.setEmailAddress("emailAddress@email.com");
    }

    @Test
    public void processBatchItemTest() throws Exception {
       EmailInfo result = emailInfoProcessor.process(emailInfo);
       assertEquals(emailInfo.getAccountId(), result.getAccountId());
       assertEquals(emailInfo.getEmailAddress(), result.getEmailAddress());
    }
}
