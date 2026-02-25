package fmfi.sbdemo.core.api.dto;

@lombok.Builder
public record PaymentSymbolsDto(
        String variable,
        String specific,
        String constant,
        String payerReference
) {}
