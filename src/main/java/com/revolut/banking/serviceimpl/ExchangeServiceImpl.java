package com.revolut.banking.serviceimpl;

import com.revolut.banking.model.Currency;
import com.revolut.banking.service.ExchangeService;
import lombok.Value;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ExchangeServiceImpl implements ExchangeService {

    @Value
    private static class CacheKey {
        String from;
        String to;
    }

    private final Map<CacheKey, BigDecimal> currentyRates;

    {
        currentyRates = new HashMap<>();
        currentyRates.put(new CacheKey("USD", "EUR"), new BigDecimal("0.8"));
        currentyRates.put(new CacheKey("EUR", "USD"), new BigDecimal("1.2"));
    }

    @Override
    public BigDecimal getCurrencyRate(Currency from, Currency to) {
        List<Currency> avaCurrency = Arrays.asList(Currency.values());
        if(!avaCurrency.contains(from) || !avaCurrency.contains(to)) {
            throw new IllegalArgumentException("Currency not available.");
        }

        if (from.equals(to)) {
            return BigDecimal.ONE;
        }
        BigDecimal rate = currentyRates.get(new CacheKey(from.getCode(), to.getCode()));
        if (rate == null) {
            throw new IllegalArgumentException("No conversion rates found for (" + from.getCode() + "," + to.getCode() + ")");
        }
        return rate;
    }
}

