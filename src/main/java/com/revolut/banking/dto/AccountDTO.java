package com.revolut.banking.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.revolut.banking.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"accountNumber", "currency", "accountBalance"})
public class AccountDTO {

    private String acctNo;
    private BigDecimal balance;
    private String currency;

    public static AccountDTO fromDomain(Account account) {
        return new AccountDTO(account.getAcctNo(), account.getBalance(), account.getCurrency().getCode());
    }
}
