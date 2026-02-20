package fmfi.sbdemo.core.api.dictionary;

public record Money(java.math.BigDecimal amount, String currency) {

    public static Money eur(long amount) {
        return new Money(java.math.BigDecimal.valueOf(amount), "EUR");
    }
}
