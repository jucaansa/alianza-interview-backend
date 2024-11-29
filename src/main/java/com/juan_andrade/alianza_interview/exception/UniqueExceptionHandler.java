package com.juan_andrade.alianza_interview.exception;

import com.juan_andrade.alianza_interview.service.impl.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UniqueExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(UniqueExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseExceptionDto> handleUniqueViolation(DataIntegrityViolationException e) {
        String errorMessage = "Sorry, client with shared_key already exists";
        BaseExceptionDto exception = new BaseExceptionDto();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setError(e.getMessage());
        exception.setMessage(errorMessage);

        logger.error("Error during save client data -> {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}
