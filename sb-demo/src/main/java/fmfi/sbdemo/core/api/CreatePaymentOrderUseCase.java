package fmfi.sbdemo.core.api;

import fmfi.sbdemo.core.api.dto.*;

public interface CreatePaymentOrderUseCase {
    PaymentOrderDto createPaymentOrder(PaymentOrderRequestDto paymentOrderRequest);
}
