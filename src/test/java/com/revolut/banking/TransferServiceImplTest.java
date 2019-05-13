package com.revolut.banking;

import com.revolut.banking.model.Account;
import com.revolut.banking.model.Currency;
import com.revolut.banking.repo.AccountRepository;
import com.revolut.banking.service.ExchangeService;
import com.revolut.banking.service.TransactionService;
import com.revolut.banking.service.TransferService;
import com.revolut.banking.serviceimpl.TransferServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TransferServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ExchangeService exchangeService;
    private TransactionService transactionService = (account1, account2, action) -> action.accept(account1, account2); // mock
    private TransferService transferService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        transferService = new TransferServiceImpl(accountRepository, exchangeService, transactionService);
        Mockito.when(exchangeService.getCurrencyRate(Currency.USD, Currency.USD))
                .thenReturn(BigDecimal.ONE);
        Mockito.when(exchangeService.getCurrencyRate(Currency.USD, Currency.EUR))
                .thenReturn(new BigDecimal("0.8"));
    }

    @Test
    public void checkUSDToUSD() {
        Account account1 = new Account("a1", BigDecimal.valueOf(10), Currency.USD);
        Account account2 = new Account("a2", BigDecimal.valueOf(20), Currency.USD);
        Mockito.when(accountRepository.findByAcctNo("a1")).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findByAcctNo("a2")).thenReturn(Optional.of(account2));

        transferService.makeTransfer("a2", "a1", new BigDecimal("1"));

        assertThat(account1.getBalance(), is(BigDecimal.valueOf(11)));
        assertThat(account2.getBalance(), is(BigDecimal.valueOf(19)));
    }


    @Test
    public void checkUSDToEU() {
        Account account1 = new Account("a1", BigDecimal.valueOf(10), Currency.USD);
        Account account2 = new Account("a2", BigDecimal.valueOf(20), Currency.EUR);
        Mockito.when(accountRepository.findByAcctNo("a1")).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findByAcctNo("a2")).thenReturn(Optional.of(account2));

        transferService.makeTransfer("a1", "a2", new BigDecimal("1"));

        assertThat(account1.getBalance(), is(BigDecimal.valueOf(9)));
        assertThat(account2.getBalance(), is(BigDecimal.valueOf(20.8)));
    }

}
