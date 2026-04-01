package sk.fmfi.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import sk.fmfi.model.Fee;
import sk.fmfi.repository.FeeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
@Transactional(Transactional.TxType.NOT_SUPPORTED)
@Log
public class FeeServiceBean implements FeeService {

    private final FeeRepository feeRepository;

    @ConfigProperty(name = "minimal.fee.limit")
    private int minimalFeeLimit;

    @Inject
    public FeeServiceBean(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Fee createFee(String transactionId, String iban, BigDecimal transactionAmount) {
        log.info("Creating fee for transactionId=" + transactionId + ", iban=" + iban + ", transactionAmount=" + transactionAmount);

        Fee fee = new Fee();
        fee.setTransactionId(transactionId);
        fee.setIban(iban);
        fee.setPostingDate(LocalDateTime.now());

        if (transactionAmount.compareTo(BigDecimal.valueOf(minimalFeeLimit)) <= 0) {
            fee.setAmount(BigDecimal.valueOf(0.01));
        } else {
            fee.setAmount(BigDecimal.valueOf(2));
        }

        feeRepository.persist(fee);
        return fee;
    }

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public List<Fee> getAllFees() {
        log.info("Getting all fees");
        return feeRepository.listAll();
    }

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public List<Fee> getFeesForIban(String iban) {
        log.info("Getting fees for iban=" + iban);
        return feeRepository.listForIban(iban);
    }
}