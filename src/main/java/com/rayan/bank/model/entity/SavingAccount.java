package com.rayan.bank.model.entity;

import javax.persistence.*;

import java.util.List;

@Entity
public class SavingAccount {



    @Id
    @GeneratedValue
    private Integer id;
    @Version
    private Integer version;
    private Long amount;
    @OneToMany
    private List<Transaction> transactions;
    @GeneratedValue
    private Integer accountNumber;
    private Long balance = 5000L ;

    private Long minBalance=balance;
    private Long sumOfMinBalances=0L;
    private Long monthlyProfit=0L;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getSumOfMinBalances() {
        return sumOfMinBalances;
    }

    public void setSumOfMinBalances(Long sumOfMinBalances) {
        this.sumOfMinBalances = sumOfMinBalances;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Long minBalance) {
        this.minBalance = minBalance;
    }

    public Long getMonthlyProfit() {
        return monthlyProfit;
    }

    public void setMonthlyProfit(Long monthlyProfit) {
        this.monthlyProfit = monthlyProfit;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }



    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }
    public Boolean withdrawal(Long amount ){
        if(amount<balance){
            balance-=amount;
            if(balance < minBalance) minBalance = balance;
            return true;
        }


        else
            return false;
    }


    public void deposit(Long amount){
        balance+=amount;
    }
    public Boolean transferAmount(Long amount,String accountNumber){
        if (amount<balance){
            balance-=amount;

            return true;
        }
        return false;

    }

}
