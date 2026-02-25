package fmfi.sbdemo.adapter.rest;

import fmfi.sbdemo.core.api.*;
import fmfi.sbdemo.core.api.dto.*;
import org.springframework.web.bind.annotation.*;

@RestController
@lombok.RequiredArgsConstructor
public class PaymentOrderRestController {

    private final CreatePaymentOrderUseCase createPaymentOrderUseCase;

    @PostMapping("/api/payment-orders")
    public PaymentOrderDto createPaymentOrder(
            @RequestBody PaymentOrderRequestDto paymentOrderRequest
    ) {
        return createPaymentOrderUseCase.createPaymentOrder(paymentOrderRequest);
    }
}
