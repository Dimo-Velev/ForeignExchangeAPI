package exchange.foreignexchangeapi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO {

    @JsonProperty("conversion_rate")
    private BigDecimal conversionRate;
    private String result;
    @JsonProperty("error-type")
    private String errorType;
    @JsonProperty("base_code")
    private String sourceCurrency;
    @JsonProperty("target_code")
    private String targetCurrency;
}
