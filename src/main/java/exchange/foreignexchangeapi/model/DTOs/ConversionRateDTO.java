package exchange.foreignexchangeapi.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ConversionRateDTO {

    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal conversionRate;
}
