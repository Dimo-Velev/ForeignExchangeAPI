package exchange.foreignexchangeapi.controllers;

import exchange.foreignexchangeapi.model.DTOs.ConversionDTO;
import exchange.foreignexchangeapi.model.DTOs.ConversionListDTO;
import exchange.foreignexchangeapi.model.DTOs.ConversionRateDTO;
import exchange.foreignexchangeapi.model.entities.Transaction;
import exchange.foreignexchangeapi.exceptions.BadRequestException;
import exchange.foreignexchangeapi.services.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Validated
@RestController
@Slf4j
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Operation(summary = "Currency pair to retrieve the exchange rate.")
    @GetMapping("/exchange")
    public ConversionRateDTO exchangeRate(@Valid @RequestParam @NotBlank(message = "Can't be blank.")
                                          @Size(min = 3, max = 3, message = "Currency must be maximum 3 letters.") String currency1,
                                          @Valid @RequestParam @NotBlank(message = "Can't be blank.")
                                          @Size(min = 3, max = 3, message = "Currency must be maximum 3 letters.") String currency2) {
        return exchangeRateService.getExchangeRates(currency1, currency2);
    }

    @Operation(summary = "Conversion of source amount, source currency, target currency to amount in target currency and transaction id.")
    @PostMapping("/conversion")
    public Transaction convertCurrency(@Valid @RequestBody ConversionDTO dto) {
        ConversionRateDTO conversionRateDTO = exchangeRateService.getExchangeRates(dto.getSourceCurrency(), dto.getTargetCurrency());
        return exchangeRateService.getConversionRate(conversionRateDTO, dto.getSourceAmount());
    }

    @Operation(summary = "Transaction id or transaction date (at least one of the inputs shall be provided for each call to get " +
            "list of conversions filtered by the inputs in paging.")
    @PostMapping("/conversions")
    public Page<Transaction> getTransactions(@Valid @RequestBody ConversionListDTO dto, @PageableDefault(size = 10)Pageable pageable){
        Long id = dto.getId();
        LocalDate transactionDate = dto.getTransactionDate();
        if (id != null && transactionDate == null){
            return exchangeRateService.getConversionListById(id,pageable);
        } else if (transactionDate != null && id == null) {
            return exchangeRateService.getConversionListByDate(transactionDate,pageable);
        } else if (id != null && transactionDate != null) {
            throw new BadRequestException("You need to provide either Id or start date plus end date, you cant search for both.");
        }else {
            throw new BadRequestException("You need to provide either Id or start date plus end date to get the transactions you want.");
        }
    }
}
