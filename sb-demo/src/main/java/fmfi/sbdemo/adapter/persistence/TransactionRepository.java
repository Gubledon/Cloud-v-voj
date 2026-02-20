package fmfi.sbdemo.adapter.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    java.util.List<TransactionEntity> findBySenderAccountIbanOrTargetAccountIbanOrderByPaymentDetailEffectiveDateDesc(
        String sourceAccountIban,
        String targetAccountIban,
        Pageable pageable
    );
}
