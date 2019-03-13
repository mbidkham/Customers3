package com.rayan.bank.controller;

import com.rayan.bank.model.dao.SavingAccountDao;
import com.rayan.bank.model.entity.SavingAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ProfitManager {

    Logger logger = LoggerFactory.getLogger(ProfitManager.class);
    private SavingAccountDao savingAccountDao;

    public ProfitManager(SavingAccountDao savingAccountDao) {
        this.savingAccountDao = savingAccountDao;
    }

    @Scheduled(cron = "59 * * * * *")
    public void profitManager() {
        List<SavingAccount> savingAccounts = savingAccountDao.dailyProfitManager();
        for (SavingAccount sa : savingAccounts) {
            sa.setMonthlyProfit(sa.getSumOfMinBalances() * 20 / 36500);
            sa.setBalance(sa.getBalance()+sa.getMonthlyProfit());
            sa.setMonthlyProfit(0L);
            savingAccountDao.save(sa);
        }
        logger.info("profitManager");


    }

    @Scheduled(cron = "40 * * * * *")
    public void minBalanceSetting() {

        List<SavingAccount> savingAccounts = savingAccountDao.dailyProfitManager();
        if(savingAccounts.size()!=0){
            for (SavingAccount sa : savingAccounts) {
                sa.setSumOfMinBalances(sa.getMinBalance() + sa.getSumOfMinBalances());
                sa.setMinBalance(sa.getBalance());
                savingAccountDao.save(sa);
            }
        }



    }
}
