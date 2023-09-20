package exchange.foreignexchangeapi.services;

import exchange.foreignexchangeapi.model.DTOs.ConversionRateDTO;
import exchange.foreignexchangeapi.model.DTOs.ExchangeRateDTO;
import exchange.foreignexchangeapi.configs.ExchangeRateConfig;
import exchange.foreignexchangeapi.model.entities.Transaction;
import exchange.foreignexchangeapi.exceptions.BadRequestException;
import exchange.foreignexchangeapi.exceptions.NotFoundException;
import exchange.foreignexchangeapi.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExchangeRateService {

    public static final String UNSUPPORTED_CODE = "unsupported-code";
    public static final String MALFORMED_REQUEST = "malformed-request";
    public static final String INVALID_KEY = "invalid-key";
    public static final String INACTIVE_ACCOUNT = "inactive-account";
    public static final String QUOTA_REACHED = "quota-reached";
    public static final String ERROR = "error";
    @Autowired
    private ExchangeRateConfig exchangeRateConfig;
    @Autowired
    private TransactionRepository transactionRepository;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ModelMapper modelMapper;

    public ConversionRateDTO getExchangeRates(String currency1, String currency2) {
        String apiUrl = exchangeRateConfig.getBaseUrl() + exchangeRateConfig.getApiKey() + "/pair/" + currency1 + "/" + currency2;
        ExchangeRateDTO dto;
        try {
            dto = restTemplate.getForObject(apiUrl, ExchangeRateDTO.class);
            return modelMapper.map(dto,ConversionRateDTO.class);
        } catch (HttpClientErrorException e) {
            String result = e.getResponseBodyAsString();
            if (result.contains(ERROR)) {
                if (result.contains(UNSUPPORTED_CODE)) {
                    throw new BadRequestException("Unsupported currency code.");
                } else if (result.contains(MALFORMED_REQUEST)) {
                    throw new BadRequestException("One or both currencies are not valid.");
                } else if (result.contains(INVALID_KEY)) {
                    throw new NotFoundException("Server API key is not valid anymore.");
                } else if (result.contains(INACTIVE_ACCOUNT)) {
                    throw new NotFoundException("API server account is inactive.");
                } else if (result.contains(QUOTA_REACHED)) {
                    throw new NotFoundException("Request quota reached, this feature will be supported again in maximum of 1 month.");
                }
            }
        }
        return null;
    }

    public Transaction getConversionRate(ConversionRateDTO dto, BigDecimal sourceAmount) {
        Transaction transaction = Transaction.builder()
                .date(LocalDate.now())
                .sourceAmount(sourceAmount)
                .sourceCurrency(dto.getSourceCurrency())
                .targetCurrency(dto.getTargetCurrency())
                .targetAmount(dto.getConversionRate().multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN))
                .build();
        transactionRepository.save(transaction);
        return transaction;
    }
    public Page<Transaction> getConversionListById(Long id, Pageable pageable) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new NotFoundException( "Id not found."));
        return new PageImpl<>(List.of(transaction), pageable,1);
    }

    public Page<Transaction> getConversionListByDate(LocalDate transactionDate, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByDate(transactionDate,pageable);
        if (transactions.isEmpty()){
           throw new NotFoundException("No transaction found for that date.");
        }
        return transactions;
    }

}
