package fmfi.sbdemo.adapter.persistence;

import fmfi.sbdemo.core.api.dictionary.*;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "transaction")
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionEntity {

    @Id @EqualsAndHashCode.Include private String transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Embedded private PaymentDetailEmbeddable paymentDetail;

    @AttributeOverride(name = "iban", column = @Column(name = "sender_account_iban"))
    @AttributeOverride(name = "name", column = @Column(name = "sender_account_name"))
    @AttributeOverride(name = "bic", column = @Column(name = "sender_account_bic"))
    @AttributeOverride(name = "balance.amount", column = @Column(name = "sender_account_balance_value"))
    @AttributeOverride(name = "balance.currency", column = @Column(name = "sender_account_balance_currency"))
    private AccountEmbeddable senderAccount;

    @AttributeOverride(name = "iban", column = @Column(name = "target_account_iban"))
    @AttributeOverride(name = "name", column = @Column(name = "target_account_name"))
    @AttributeOverride(name = "bic", column = @Column(name = "target_account_bic"))
    @AttributeOverride(name = "balance.amount", column = @Column(name = "target_account_balance_value"))
    @AttributeOverride(name = "balance.currency", column = @Column(name = "target_account_balance_currency"))
    private AccountEmbeddable targetAccount;

    @Embedded private PaymentSymbolsEmbeddable paymentSymbols;
}
