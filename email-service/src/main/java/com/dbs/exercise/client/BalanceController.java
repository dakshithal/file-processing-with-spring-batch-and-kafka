package com.dbs.exercise.client;

import com.dbs.exercise.model.CashBalance;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account-service")
public interface BalanceController {

    @RequestMapping("/balance/{accountId}")
    CashBalance getBalance(@PathVariable("accountId") String accountId);

    @RequestMapping("/balance/lastOffset")
    Long getLastReceivedMsgOffset();
}
