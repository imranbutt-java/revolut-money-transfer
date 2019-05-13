package com.revolut.banking;

import com.revolut.banking.model.Account;
import com.revolut.banking.model.Currency;
import com.revolut.banking.repo.AccountRepository;
import lombok.val;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.stream.IntStream;

public class PrepareApplication {

    public static void populateAccounts(AccountRepository rep) {
        Random r = new Random();
        int tmp = 2;
        IntStream.range(1, 10).mapToObj(i -> {
                    Currency currency = i < 5 ? Currency.USD : Currency.EUR;
                    Account a = new Account("R-" + i, new BigDecimal(tmp * i), currency);
                    return a;
                }).forEach(rep::save);
    }
}
