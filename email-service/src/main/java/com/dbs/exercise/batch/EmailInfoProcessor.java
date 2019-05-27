package com.dbs.exercise.batch;

import com.dbs.exercise.model.EmailInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmailInfoProcessor implements ItemProcessor<EmailInfo, EmailInfo> {
    /**
     * Process step of spring batch job for file read
     * @param emailInfo : The email info object of the read batch item
     * @return EmailInfo object after processing done on it
     */
    @Override
    public EmailInfo process(EmailInfo emailInfo) {
        System.out.println("Email Info from file processed : " + emailInfo.toString());
        return emailInfo;
    }
}
