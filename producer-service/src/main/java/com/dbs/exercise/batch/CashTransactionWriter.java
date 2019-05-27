package com.dbs.exercise.batch;

import com.dbs.exercise.model.CashTransaction;
import com.dbs.exercise.service.KafkaProducerService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CashTransactionWriter implements ItemWriter<CashTransaction> {
    @Autowired
    KafkaProducerService kafkaProducerService;

    /**
     * Write step of spring batch file process job
     * @param cashTransactions : List of processed batch items of file read job
     */
    @Override
    public void write(List<? extends CashTransaction> cashTransactions) {
        cashTransactions.forEach(tx->{
            System.out.println("Cash Transaction from file written to kafka : " + tx.toString());
            kafkaProducerService.publishCashTransaction(tx);
        });
    }
}
