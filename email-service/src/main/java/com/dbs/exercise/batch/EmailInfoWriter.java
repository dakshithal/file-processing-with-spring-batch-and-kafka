package com.dbs.exercise.batch;

import com.dbs.exercise.model.EmailInfo;
import com.dbs.exercise.service.EmailService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailInfoWriter implements ItemWriter<EmailInfo> {
    @Autowired
    EmailService emailService;

    /**
     * Write step of spring batch file process job
     * @param emailInfoList : List of processed batch items of file read job
     */
    @Override
    public void write(List<? extends EmailInfo> emailInfoList) {
        emailInfoList.forEach(emailInfo -> {
            emailService.sendEmail(emailInfo);
        });
    }
}
