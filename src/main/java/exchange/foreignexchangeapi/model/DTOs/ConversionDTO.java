package exchange.foreignexchangeapi.model.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ConversionDTO {

    @NotNull
    @Min(value = 0, message = "Amount must be 0 or greater than 0.")
    private BigDecimal sourceAmount;
    @NotBlank
    @Size(min = 3,max = 3, message = "Source currency must contain 3 letters.")
    private String sourceCurrency;
    @NotBlank
    @Size(min = 3,max = 3, message = "Target currency must contain 3 letters.")
    private String targetCurrency;
}
