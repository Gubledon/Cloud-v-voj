package fmfi.sbdemo.core.api.dto;

@lombok.Builder
public record PaymentOrderRequestDto(
        String senderAccountIban,
        String targetAccountIban,
        PaymentSymbolsDto paymentSymbols,
        PaymentDetailDto paymentDetail
) {}
