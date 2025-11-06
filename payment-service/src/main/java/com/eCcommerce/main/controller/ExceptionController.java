package com.eCcommerce.main.controller;

import com.eCcommerce.main.dto.ErrorDto;
import com.eCcommerce.main.exception.ExcessPaymentException;
import com.eCcommerce.main.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ErrorDto> handleExcessPaymentException(ExcessPaymentException exception){

        ErrorDto errorDto= ErrorDto.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDto.getStatusCode()).body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException exception){

        ErrorDto errorDto= ErrorDto.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDto.getStatusCode()).body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleGeneralException(Exception exception){

        ErrorDto errorDto= ErrorDto.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDto.getStatusCode()).body(errorDto);
    }
}
