package exchange.foreignexchangeapi.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ExchangeRateConfig {

    @Value("${exchange-rate.api.base-url}")
    private String baseUrl;
    @Value("${exchange-rate.api.api-key}")
    private String apiKey;
}
