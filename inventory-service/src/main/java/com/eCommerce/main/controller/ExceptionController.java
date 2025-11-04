package com.eCommerce.main.controller;

import com.eCommerce.main.dto.ErrorDto;
import com.eCommerce.main.exceptions.InsufficientStockException;
import com.eCommerce.main.exceptions.ProductNotFoundException;
import jakarta.validation.ConstraintDeclarationException;
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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ProductNotFoundException ex) {
        ErrorDto error = ErrorDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorDto> handleInsufficientStockException(InsufficientStockException ex) {
        ErrorDto error = ErrorDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleGeneralError(Exception exception){

        ErrorDto errorDto=ErrorDto
                .builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDto.getHttpStatus()).body(errorDto);
    }
}
