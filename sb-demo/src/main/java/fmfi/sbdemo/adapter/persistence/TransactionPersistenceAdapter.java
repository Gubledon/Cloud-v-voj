package fmfi.sbdemo.adapter.persistence;

import fmfi.sbdemo.core.api.dictionary.*;
import fmfi.sbdemo.core.api.dto.*;
import fmfi.sbdemo.core.domain.*;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Component
@lombok.RequiredArgsConstructor
public class TransactionPersistenceAdapter implements FindLastCurrentAccountTransactionsByAccountNumberSpi {

    private final TransactionRepository repository;

    private final TransactionEntityMapper mapper = new TransactionEntityMapper();

    @Override
    public CurrentAccountTransactionsDto findLastCurrentAccountTransactionsByAccountNumber(
        String accountNumber,
        int transactionRecordCount
    ) {

        var transactionRecords = repository
            .findBySenderAccountIbanOrTargetAccountIbanOrderByPaymentDetailEffectiveDateDesc(
                accountNumber,
                accountNumber,
                Pageable.ofSize(transactionRecordCount)
            );

        return new CurrentAccountTransactionsDto(
            transactionRecords.stream().map(t -> mapper.toDto(t, accountNumber)).toList()
        );
    }
}

class TransactionEntityMapper {

    CurrentAccountTransactionListItemDto toDto(TransactionEntity entity, String accountNumber) {

        var partnerAccount = getPartnerAccount(entity, accountNumber);
        var ownAccount = getOwnAccount(entity, accountNumber);

        return CurrentAccountTransactionListItemDto.builder()
            .transactionId(entity.getTransactionId())
            .amount(entity.getPaymentDetail().getAmount().toMoney())
            .accountBalance(ownAccount.getBalance().toMoney())
            .variableSymbol(entity.getPaymentSymbols() instanceof PaymentSymbolsEmbeddable ps ? ps.getVariable() : null)
            .partnerName(partnerAccount.getName())
            .description(entity.getPaymentDetail().getDescription())
            .effectiveDate(entity.getPaymentDetail().getEffectiveDate())
            .status(entity.getStatus())
            .direction(
                accountNumber.equals(entity.getSenderAccount().getIban())
                    ? TransactionDirection.DEBIT : TransactionDirection.CREDIT)
            .build();
    }

    private AccountEmbeddable getPartnerAccount(TransactionEntity entity, String accountNumber) {
        if (accountNumber.equals(entity.getSenderAccount().getIban())) {
            return entity.getTargetAccount();
        }
        if (accountNumber.equals(entity.getTargetAccount().getIban())) {
            return entity.getSenderAccount();
        }
        throw new IllegalArgumentException("Specified accountNumber is neither source nor target account iban.");
    }

    private AccountEmbeddable getOwnAccount(TransactionEntity entity, String accountNumber) {
        if (accountNumber.equals(entity.getSenderAccount().getIban())) {
            return entity.getSenderAccount();
        }
        if (accountNumber.equals(entity.getTargetAccount().getIban())) {
            return entity.getTargetAccount();
        }
        throw new IllegalArgumentException("Specified accountNumber is neither source nor target account iban.");
    }
}
