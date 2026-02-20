package fmfi.sbdemo.adapter.persistence;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
class PaymentSymbolsEmbeddable {
    @Column(name = "variable_symbol") private String variable;
    @Column(name = "specific_symbol") private String specific;
    @Column(name = "constant_symbol") private String constant;
    private String payerReference;
}
