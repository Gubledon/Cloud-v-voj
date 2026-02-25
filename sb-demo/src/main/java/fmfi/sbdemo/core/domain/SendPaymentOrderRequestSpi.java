package fmfi.sbdemo.core.domain;

import fmfi.sbdemo.core.api.dto.*;

public interface SendPaymentOrderRequestSpi {
    PaymentOrderDto sendPaymentOrderRequest(PaymentOrderRequestDto paymentOrderRequest);
}
