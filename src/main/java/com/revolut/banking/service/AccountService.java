package com.revolut.banking.service;

import com.revolut.banking.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAll();
    Optional<Account> getAccount(String id);
}