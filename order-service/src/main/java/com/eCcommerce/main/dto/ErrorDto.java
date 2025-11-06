package com.eCcommerce.main.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDto {

    private String message;
    private HttpStatus httpStatus;
    private Integer statusCode;
    private LocalDateTime timeStamp;
}
