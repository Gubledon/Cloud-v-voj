package fmfi.sbdemo.core.domain;

import fmfi.sbdemo.core.api.*;
import fmfi.sbdemo.core.api.dto.*;

@org.springframework.stereotype.Service
@lombok.RequiredArgsConstructor
public class CurrentAccountTransactionService implements GetLatestCurrentAccountTransactionsUseCase {

    private final FindLastCurrentAccountTransactionsByAccountNumberSpi findTransactionsSpi;

    private final CurrentAccountTransactionConfigProperties configProperties;

    @Override
    public CurrentAccountTransactionsDto getLatestCurrentAccountTransactions(String accountNumber) {
        // TODO check logged user permission for account

        return findTransactionsSpi.findLastCurrentAccountTransactionsByAccountNumber(
            accountNumber,
            configProperties.getLatestCurrentAccountTransactionsMaxRecordCount()
        );
    }
}
