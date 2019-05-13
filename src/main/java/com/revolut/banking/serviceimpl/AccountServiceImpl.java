package com.revolut.banking.serviceimpl;

import com.revolut.banking.model.Account;
import com.revolut.banking.repo.AccountRepository;
import com.revolut.banking.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.getAll().stream().collect(toList());
    }

    @Override
    public Optional<Account> getAccount(String id) {
        return accountRepository.findByAcctNo(id);
    }
}
