package com.revolut.banking.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final ReentrantLock lock = new ReentrantLock();

    public Account(String accountNumber, BigDecimal accountBalance, Currency currency) {
        this.acctNo = accountNumber;
        this.currency = currency;
        this.balance = accountBalance;
    }

    // Account No and currency won't be set
    @Getter
    private String acctNo;
    @Getter
    private Currency currency;

    @Getter
    @Setter
    private volatile BigDecimal balance;

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }


}
