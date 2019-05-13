package com.revolut.banking.serviceimpl;

import com.revolut.banking.model.Account;
import com.revolut.banking.repo.AccountRepository;
import com.revolut.banking.service.ExchangeService;
import com.revolut.banking.service.TransactionService;
import com.revolut.banking.service.TransferService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BiConsumer;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final ExchangeService exchangeService;
    private final TransactionService transactionService;

    @Override
    public void makeTransfer(String accountFromNum, String accountToNum, BigDecimal amount) {

        Account accountFrom = getAccountByNumber(accountFromNum);
        Account accountTo = getAccountByNumber(accountToNum);

        BigDecimal currencyRate = exchangeService.getCurrencyRate(accountFrom.getCurrency(), accountTo.getCurrency());
        BigDecimal transferedAmountTo = amount.multiply(currencyRate);

        transactionService.doTransaction(accountFrom, accountTo, (from, to) -> {

            if (from.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException("Account " + accountFromNum + " has insufficient funds (expected transfer amount: " + amount + "; actual: " + from.getBalance());
            }

            from.setBalance(from.getBalance().subtract(amount));
            to.setBalance(to.getBalance().add(transferedAmountTo));
        });

    }

    private Account getAccountByNumber(String accountNum) {
        return accountRepository.findByAcctNo(accountNum)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountNum + " not found!"));
    }
}
