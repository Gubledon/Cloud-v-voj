package fmfi.sbdemo.core.api.dto;

import fmfi.sbdemo.core.api.dictionary.*;

@lombok.Builder
public record CurrentAccountTransactionListItemDto(
    String transactionId,
    Money amount,
    String partnerName,
    String variableSymbol,
    String description,
    Money accountBalance,
    java.time.LocalDate effectiveDate,
    TransactionStatus status,
    TransactionDirection direction
) { }
