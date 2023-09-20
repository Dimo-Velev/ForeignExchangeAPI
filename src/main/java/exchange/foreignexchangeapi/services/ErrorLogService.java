package exchange.foreignexchangeapi.services;

import exchange.foreignexchangeapi.model.entities.ErrorLog;
import exchange.foreignexchangeapi.repositories.ErrorLogRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrorLogService {

    @Autowired
    private ErrorLogRepository errorLogRepository;


    public void logError(Exception e){
        ErrorLog errorLog = ErrorLog.builder()
                .errorMessage(e.getMessage())
                .stackTrace(ExceptionUtils.getStackTrace(e))
                .timeStamp(LocalDateTime.now())
                .build();
        errorLogRepository.save(errorLog);
    }
}
