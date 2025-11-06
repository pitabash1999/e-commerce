package com.eCcommerce.main.controller;

import com.eCcommerce.main.dto.ErrorDto;
import com.eCcommerce.main.exception.InsufficientStockException;
import com.eCcommerce.main.exception.OrderNotFoundException;
import com.eCcommerce.main.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDto>> handleValidationException(MethodArgumentNotValidException validationErrors){

        List<ErrorDto> list = validationErrors
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorDto.builder()
                        .message(error.getDefaultMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timeStamp(LocalDateTime.now())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleInsufficientStock(InsufficientStockException ex) {

        ErrorDto error = ErrorDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleProductNotFound(ProductNotFoundException ex) {

        ErrorDto error = ErrorDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleOrderNotFound(OrderNotFoundException ex) {

        ErrorDto error = ErrorDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleGeneralException(Exception exception){
        exception.printStackTrace();
        ErrorDto errorDto= ErrorDto.builder()
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timeStamp(LocalDateTime.now())
                        .build();
        return ResponseEntity.status(errorDto.getStatusCode()).body(errorDto);
    }
}
