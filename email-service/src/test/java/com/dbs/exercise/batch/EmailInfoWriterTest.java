package com.dbs.exercise.batch;

import com.dbs.exercise.model.EmailInfo;
import com.dbs.exercise.service.EmailService;
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
@WebMvcTest(EmailInfoWriter.class)
public class EmailInfoWriterTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmailInfoWriter emailInfoWriter;

    @MockBean
    private EmailService emailService;

    private EmailInfo emailInfo;

    @Before
    public void setUp(){
        emailInfo = new EmailInfo();
        emailInfo.setAccountId("A");
        emailInfo.setEmailAddress("emailAddress@email.com");
    }

    @Test
    public void writeBatchItemTest() {
        Mockito.doNothing().when(emailService).sendEmail(emailInfo);

        List<EmailInfo> emailInfoList = new ArrayList<>();
        emailInfoList.add(emailInfo);
        emailInfoList.add(emailInfo);
        emailInfoList.add(emailInfo);
        emailInfoWriter.write(emailInfoList);

        verify(emailService,times(3)).sendEmail(emailInfo);
    }
}
