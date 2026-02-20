package fmfi.sbdemo.core.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.current-account.transaction")
public record CurrentAccountTransactionConfigProperties(
    int getLatestCurrentAccountTransactionsMaxRecordCount
) { }
