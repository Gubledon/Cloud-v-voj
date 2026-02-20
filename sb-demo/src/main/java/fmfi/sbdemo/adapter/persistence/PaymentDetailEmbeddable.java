package fmfi.sbdemo.adapter.persistence;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
class PaymentDetailEmbeddable {
    private MoneyEmbeddable amount;
    private java.time.LocalDate effectiveDate;
    private String description;
}
