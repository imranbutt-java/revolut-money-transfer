package com.revolut.banking.repo;

import com.revolut.banking.model.Account;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> accountStorage = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {
        return accountStorage.put(account.getAcctNo(), account);
    }

    @Override
    public void delete(Account account) {
        accountStorage.remove(account.getAcctNo());
    }

    @Override
    public Collection<Account> getAll() {
        return accountStorage.values();
    }

    @Override
    public Optional<Account> findByAcctNo(String accountNumber) { return Optional.ofNullable(accountStorage.get(accountNumber));  }

    public void clear() {
        accountStorage.clear();
    }
}
