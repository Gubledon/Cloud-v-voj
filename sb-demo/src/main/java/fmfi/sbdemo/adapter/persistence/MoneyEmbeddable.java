package fmfi.sbdemo.adapter.persistence;

import fmfi.sbdemo.core.api.dictionary.Money;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
class MoneyEmbeddable {
    private java.math.BigDecimal amount;
    private String currency;

    public Money toMoney() { return new Money(amount, currency); }
}
