package com.revolut.banking.controller;

import com.revolut.banking.controller.request.TransferRequest;
import com.revolut.banking.dto.AccountDTO;
import com.revolut.banking.model.Account;
import com.revolut.banking.service.AccountService;
import com.revolut.banking.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.jooby.Err;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.*;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/accounts")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AccountController {

    private final TransferService transferService;
    private final AccountService accountService;


    @GET
    public List<AccountDTO> getAll() {
        return accountService.getAll().stream()
                .map(AccountDTO::fromDomain)
                .sorted(Comparator.comparing(AccountDTO::getAcctNo))
                .collect(toList());
    }

    @Path("/{id}")
    @GET
    public AccountDTO getOne(String id) {
        return accountService.getAccount(id)
                .map(AccountDTO::fromDomain)
                .orElseThrow(() -> new Err(404));
    }

    @Path("/transfer")
    @POST
    public Result makeTransfer(@Body TransferRequest transferRequest) {
        transferService.makeTransfer(transferRequest.getAccountFrom(), transferRequest.getAccountTo(), transferRequest.getAmount());
        return Results.ok();
    }

}