package exchange.foreignexchangeapi.controllers;

import exchange.foreignexchangeapi.model.DTOs.ErrorDTO;
import exchange.foreignexchangeapi.exceptions.BadRequestException;
import exchange.foreignexchangeapi.exceptions.NotFoundException;
import exchange.foreignexchangeapi.services.ErrorLogService;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.BindException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private ErrorLogService errorLogService;

    @ExceptionHandler({BadRequestException.class, MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleBadRequest(Exception e) {
        log.error(e.getMessage());
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleNotFound(Exception e) {
        log.error(e.getMessage());
        return generateErrorDTO(e, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Error.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ErrorDTO handleError(Exception e){
//        return generateErrorDTO(e,HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleRest(Exception e) {
        log.error(e.getMessage());
        errorLogService.logError(e);
        return generateErrorDTO(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleClientErrorException(Exception e) {
        log.error(e.getMessage());
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }

    private ErrorDTO generateErrorDTO(Exception e, HttpStatus status) {
        return ErrorDTO.builder()
                .msg(e.getMessage())
                .time(LocalDateTime.now())
                .status(status.value())
                .build();
    }
}
