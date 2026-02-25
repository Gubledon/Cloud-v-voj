package fmfi.sbdemo.core.domain;

import fmfi.sbdemo.core.api.dto.*;

public interface ApplyFeesForPaymentOrderSpi {
    void applyFeesForPaymentOrder(PaymentOrderDto paymentOrder);
}
