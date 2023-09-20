package exchange.foreignexchangeapi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
public class ConversionListDTO {

    @Min(value = 1, message = "Id can't be below 1.")
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format must be yyyy-MM-dd.")
    private LocalDate transactionDate;
}
