package fmfi.sbdemo.adapter.persistence;

import java.util.*;

import fmfi.sbdemo.core.api.dictionary.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.assertj.core.api.Assertions.*;

@org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
class TransactionPersistenceAdapterTest {

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {

        @org.springframework.context.annotation.Bean
        TransactionPersistenceAdapter testPersistenceAdapter(TransactionRepository repository) {
            return new TransactionPersistenceAdapter(repository);
        }
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    @Qualifier("testPersistenceAdapter")
    private TransactionPersistenceAdapter transactionAdapter;

    @org.junit.jupiter.api.Test
    void findBySenderAccountIbanOrTargetAccountIbanOrderByPaymentDetailEffectiveDateDesc() {
        // setup
        String accountNumber = "SK8975000000000012345671";

        var debitTransaction = TransactionEntity.builder()
            .transactionId(UUID.randomUUID().toString())
            .status(TransactionStatus.PROCESSED)
            .paymentDetail(PaymentDetailEmbeddable.builder()
                .amount(new MoneyEmbeddable(java.math.BigDecimal.valueOf(42), "EUR"))
                .effectiveDate(java.time.LocalDate.of(2022, 9, 24))
                .description("Anvil")
                .build())
            .senderAccount(AccountEmbeddable.builder()
                .iban(accountNumber)
                .balance(new MoneyEmbeddable(java.math.BigDecimal.valueOf(958), "EUR"))
                .build())
            .targetAccount(AccountEmbeddable.builder()
                .iban("DE89370400440532013000")
                .name("Acme corp.")
                .bic("DEUTDEFFXXX")
                .build())
            .paymentSymbols(PaymentSymbolsEmbeddable.builder()
                .variable("123456")
                .build())
            .build();

        var creditTransaction = TransactionEntity.builder()
            .transactionId(UUID.randomUUID().toString())
            .status(TransactionStatus.PROCESSED)
            .paymentDetail(PaymentDetailEmbeddable.builder()
                .amount(new MoneyEmbeddable(java.math.BigDecimal.valueOf(12), "EUR"))
                .effectiveDate(java.time.LocalDate.of(2022, 9, 25))
                .description("Cashback")
                .build())
            .senderAccount(AccountEmbeddable.builder()
                .iban("DE89370400440532013000")
                .name("Acme corp.")
                .bic("DEUTDEFFXXX")
                .build())
            .targetAccount(AccountEmbeddable.builder()
                .iban(accountNumber)
                .balance(new MoneyEmbeddable(java.math.BigDecimal.valueOf(970), "EUR"))
                .build())
            .build();

        var otherAccountTransaction = TransactionEntity.builder()
            .transactionId(UUID.randomUUID().toString())
            .status(TransactionStatus.PROCESSED)
            .paymentDetail(PaymentDetailEmbeddable.builder()
                .amount(new MoneyEmbeddable(java.math.BigDecimal.valueOf(10), "EUR"))
                .effectiveDate(java.time.LocalDate.of(2022, 9, 26))
                .description("Cashback")
                .build())
            .senderAccount(AccountEmbeddable.builder()
                .iban("DE89370400440532013000")
                .name("Acme corp.")
                .bic("DEUTDEFFXXX")
                .build())
            .targetAccount(AccountEmbeddable.builder()
                .iban("SK3112000000198742637541")
                .balance(new MoneyEmbeddable(java.math.BigDecimal.valueOf(1010), "EUR"))
                .build())
            .build();

        transactionRepository.saveAll(
            List.of(debitTransaction, creditTransaction, otherAccountTransaction)
        );

        // run
        var result = transactionAdapter
            .findLastCurrentAccountTransactionsByAccountNumber(accountNumber, 10);

        // verify
        assertThat(result.transactions().stream().map(it -> it.transactionId()).toList())
            .isEqualTo(List.of(creditTransaction.getTransactionId(), debitTransaction.getTransactionId()));
    }
}
