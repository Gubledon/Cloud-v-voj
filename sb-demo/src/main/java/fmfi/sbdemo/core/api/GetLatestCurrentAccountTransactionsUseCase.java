package fmfi.sbdemo.core.api;

import fmfi.sbdemo.core.api.dto.*;

public interface GetLatestCurrentAccountTransactionsUseCase {
    CurrentAccountTransactionsDto getLatestCurrentAccountTransactions(String accountNumber);
}
