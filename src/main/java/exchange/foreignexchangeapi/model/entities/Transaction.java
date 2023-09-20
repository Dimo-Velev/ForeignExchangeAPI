package exchange.foreignexchangeapi.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private  Long id;
    @Column
    private LocalDate date;
    @Column
    private String sourceCurrency;
    @Column
    private BigDecimal sourceAmount;
    @Column
    private String targetCurrency;
    @Column
    private BigDecimal targetAmount;
}
