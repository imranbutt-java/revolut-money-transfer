package com.revolut.banking;

import com.revolut.banking.controller.AccountController;
import com.revolut.banking.repo.AccountRepository;
import com.revolut.banking.repo.InMemoryAccountRepository;
import com.revolut.banking.service.AccountService;
import com.revolut.banking.service.ExchangeService;
import com.revolut.banking.service.TransactionService;
import com.revolut.banking.service.TransferService;
import com.revolut.banking.serviceimpl.AccountServiceImpl;
import com.revolut.banking.serviceimpl.ExchangeServiceImpl;
import com.revolut.banking.serviceimpl.TransactionServiceImpl;
import com.revolut.banking.serviceimpl.TransferServiceImpl;
import io.swagger.models.Scheme;
import org.jooby.apitool.ApiTool;
import org.jooby.json.Jackson;
import org.jooby.Jooby;
import org.jooby.apitool.ApiTool;
import org.jooby.json.Jackson;

import java.util.Arrays;


public class Application extends Jooby {
    {

        /** A module with domain logic */
        use((env, conf, binder) -> {
            binder.bind(AccountRepository.class).to(InMemoryAccountRepository.class);
            binder.bind(AccountService.class).to(AccountServiceImpl.class);
            binder.bind(TransactionService.class).to(TransactionServiceImpl.class);
            binder.bind(ExchangeService.class).to(ExchangeServiceImpl.class);
            binder.bind(TransferService.class).to(TransferServiceImpl.class);
        });

        /** Actual routes */
        use(AccountController.class);

        use(new Jackson());

        use(new ApiTool().swagger(swagger -> {
                    swagger.schemes(Arrays.asList(new Scheme[] {Scheme.HTTP}));
                }));

        /** Populate the repository with test accounts! */
        onStart(reg -> PrepareApplication.populateAccounts(reg.require(AccountRepository.class)));

    }

    public static void main(final String[] args) {
        run(Application::new, args);
    }
}
