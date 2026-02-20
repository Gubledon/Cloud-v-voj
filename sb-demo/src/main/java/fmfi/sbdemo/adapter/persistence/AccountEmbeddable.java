package fmfi.sbdemo.adapter.persistence;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
class AccountEmbeddable {
    @Column(nullable = false)
    private String iban;
    private String name;
    private String bic;
    private MoneyEmbeddable balance;
}
