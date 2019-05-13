package com.revolut.banking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {

    USD("USD", 1),
    EUR("EUR", 2);

    private final String code;
    private final int no;
}
