package com.revolut.banking.repo;

import com.revolut.banking.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    void delete(Account account);
    Collection<Account> getAll();
    Optional<Account> findByAcctNo(String acctNo);
}
