package com.revolut.banking.service;

import com.revolut.banking.model.Currency;

import java.math.BigDecimal;

public interface ExchangeService {
    BigDecimal getCurrencyRate(Currency from, Currency to);
}
