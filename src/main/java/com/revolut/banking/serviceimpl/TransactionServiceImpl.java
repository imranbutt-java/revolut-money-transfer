package com.revolut.banking.serviceimpl;

import com.revolut.banking.model.Account;
import com.revolut.banking.service.TransactionService;

import java.util.function.BiConsumer;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public void doTransaction(Account account1, Account account2, BiConsumer<Account, Account> action) {
        try {
            account1.lock();
            try {
                account2.lock();
                action.accept(account1, account2);
            } finally {
                account2.unlock();
            }
        } finally {
            account1.unlock();
        }
    }
}
