package fmfi.sbdemo.adapter.rest;

import fmfi.sbdemo.core.api.*;
import fmfi.sbdemo.core.api.dto.*;
import org.springframework.web.bind.annotation.*;

@RestController
@lombok.RequiredArgsConstructor
public class TransactionRestController {

    private final GetLatestCurrentAccountTransactionsUseCase getLatestCurrentAccountTransactionsUseCase;

    @GetMapping("/api/current-accounts/{accountNumber}/transactions")
    public CurrentAccountTransactionsDto getLatestCurrentAccountTransactions(
        @PathVariable(name = "accountNumber") String accountNumber
    ) {
        return getLatestCurrentAccountTransactionsUseCase.getLatestCurrentAccountTransactions(accountNumber);
    }
}
