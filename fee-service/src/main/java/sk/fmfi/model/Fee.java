package sk.fmfi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Fee {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String transactionId;

    @Column
    private String iban;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDateTime postingDate;
}