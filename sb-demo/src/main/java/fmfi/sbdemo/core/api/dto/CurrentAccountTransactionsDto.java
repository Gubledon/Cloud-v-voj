package fmfi.sbdemo.core.api.dto;

public record CurrentAccountTransactionsDto(
    java.util.List<CurrentAccountTransactionListItemDto> transactions
) {

    public CurrentAccountTransactionsDto(CurrentAccountTransactionListItemDto... transactions) {
        this(java.util.List.of(transactions));
    }
}
