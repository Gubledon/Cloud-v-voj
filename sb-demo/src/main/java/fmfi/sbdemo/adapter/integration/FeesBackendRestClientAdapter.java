package fmfi.sbdemo.adapter.integration;

import fmfi.sbdemo.core.api.dto.*;
import fmfi.sbdemo.core.domain.*;
import org.springframework.web.client.RestClient;

@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
@lombok.RequiredArgsConstructor
public class FeesBackendRestClientAdapter implements ApplyFeesForPaymentOrderSpi {

    private final RestClient feesRestClient;

    @Override
    public void applyFeesForPaymentOrder(PaymentOrderDto paymentOrder) {
        var feeRequest = FeeDto.builder()
                .transactionId(paymentOrder.transactionId())
                .acno(paymentOrder.senderAccountIban())
                .transactionAmount(paymentOrder.paymentDetail().amount().amount())
                .build();

        var response = feesRestClient.post()
                .uri("/fee")
                .body(feeRequest)
                .retrieve()
                .toBodilessEntity();

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.warn("Fees not applied for payment order {}", paymentOrder);
        }
    }
}

@lombok.Builder
record FeeDto(
        String transactionId,
        String acno,
        java.math.BigDecimal transactionAmount
) {}
