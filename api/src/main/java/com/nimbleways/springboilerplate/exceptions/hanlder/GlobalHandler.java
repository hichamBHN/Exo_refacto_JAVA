package com.nimbleways.springboilerplate.exceptions.hanlder;

import com.nimbleways.springboilerplate.dto.product.ResponseEntity;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity OrderNotFoundExceptionHandler(OrderNotFoundException e) {
        log.error("Handling order not found exception:  {}", e.getMessage());
        return ResponseEntity.V1.buildNotFoundResponse(List.of(e.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity exceptionHandler(Exception e) {
        log.error("Handling internal server error exception:  {}", e.getMessage());
        return ResponseEntity.V1.buildInternalServerResponse(List.of(e.getMessage()));
    }
}
