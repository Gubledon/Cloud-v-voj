package sk.fmfi.resource.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionDTO {

    private String transactionId;
    private String iban;
    private BigDecimal amount;
}