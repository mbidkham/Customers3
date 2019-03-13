package com.rayan.bank.model.dao;


import com.rayan.bank.model.entity.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavingAccountDao  extends JpaRepository<SavingAccount,Integer> {
    public SavingAccount findByAccountNumber(Integer accountNumber);
    @Query("select c from SavingAccount c ")
    public List<SavingAccount> dailyProfitManager();
}
