package com.eCommerce.main.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ErrorDto {

    private String message;
    private HttpStatus httpStatus;
    private Integer statusCode;
    private LocalDateTime timeStamp;
}
