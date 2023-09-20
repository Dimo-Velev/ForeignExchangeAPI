package exchange.foreignexchangeapi.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class FullInfoDTO {

    private String result;
    private String documentation;
    private String termsOfUse;
    private String timeLastUpdateUnix;
    private String timeLastUpdateUtc;
    private String timeNextUpdateUnix;
    private String timeNextUpdateUtc;
    private String baseCode;
    private String targetCode;
    private BigDecimal conversionRate;

}
