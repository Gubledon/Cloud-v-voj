package fmfi.sbdemo.core.api.dto;

import fmfi.sbdemo.core.api.dictionary.*;

@lombok.Builder
public record PaymentDetailDto(
        Money amount,
        java.time.LocalDate effectiveDate,
        String description
) {}
