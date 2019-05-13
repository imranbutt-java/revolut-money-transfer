package com.revolut.banking.service;

import com.revolut.banking.model.Account;

import java.util.function.BiConsumer;

public interface TransactionService {
    void doTransaction(Account account1, Account account2, BiConsumer<Account, Account> action);
}
