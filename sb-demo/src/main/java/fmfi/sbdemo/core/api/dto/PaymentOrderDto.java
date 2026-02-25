package fmfi.sbdemo.core.api.dto;

import fmfi.sbdemo.core.api.dictionary.*;

@lombok.Builder
public record PaymentOrderDto(
        String transactionId,
        TransactionStatus status,
        java.time.LocalDate processingDate,
        String senderAccountIban,
        String targetAccountIban,
        PaymentSymbolsDto paymentSymbols,
        PaymentDetailDto paymentDetail
) {}
