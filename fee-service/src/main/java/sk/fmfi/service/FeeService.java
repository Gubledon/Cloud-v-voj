package sk.fmfi.service;

import sk.fmfi.model.Fee;
import java.math.BigDecimal;
import java.util.List;

public interface FeeService {

    Fee createFee(String transactionId, String iban, BigDecimal transactionAmount);

    List<Fee> getAllFees();

    List<Fee> getFeesForIban(String iban);
}