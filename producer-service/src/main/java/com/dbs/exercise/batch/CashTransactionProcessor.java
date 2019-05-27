package com.dbs.exercise.batch;

import com.dbs.exercise.model.CashTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CashTransactionProcessor implements ItemProcessor<CashTransaction, CashTransaction> {
    /**
     * Process step of spring batch job for file read
     * @param cashTransaction : The cash transaction object of the read batch item
     * @return Cash Transaction object after processing done on it
     */
    @Override
    public CashTransaction process(CashTransaction cashTransaction) {
        System.out.println("Cash Transaction from file processed : " + cashTransaction.toString());
        return cashTransaction;
    }
}
