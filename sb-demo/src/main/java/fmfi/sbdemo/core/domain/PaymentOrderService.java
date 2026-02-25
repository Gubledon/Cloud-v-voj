package fmfi.sbdemo.core.domain;

import fmfi.sbdemo.core.api.*;
import fmfi.sbdemo.core.api.dto.*;

@org.springframework.stereotype.Service
@lombok.RequiredArgsConstructor
public class PaymentOrderService implements CreatePaymentOrderUseCase {

    private final SendPaymentOrderRequestSpi sendPaymentOrderRequestSpi;

    private final ApplyFeesForPaymentOrderSpi applyFeesForPaymentOrderSpi;

    @Override
    public PaymentOrderDto createPaymentOrder(PaymentOrderRequestDto paymentOrderRequest) {

        // TODO check logged user permission for account

        // TODO validate payment order request

        var paymentOrder = sendPaymentOrderRequestSpi.sendPaymentOrderRequest(paymentOrderRequest);

        applyFeesForPaymentOrderSpi.applyFeesForPaymentOrder(paymentOrder);

        return paymentOrder;
    }
}
