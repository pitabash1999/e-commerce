package com.eCcommerce.main.dto;

import com.eCcommerce.main.exception.InsufficientStockException;
import com.eCcommerce.main.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    private final ErrorDecoder defaultDecoder = new Default();

    public CustomErrorDecoder() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() != null) {
                InputStream body=response.body().asInputStream();
                ErrorDto errorDto = objectMapper.readValue(body, ErrorDto.class);
                String message = errorDto.getMessage();
                if (message != null && message.contains(":")) {
                    message = message.substring(message.indexOf(":") + 1).trim();
                }
                if (message != null && message.contains("Insufficient stock for product: ")) {
                    return new InsufficientStockException(message);
                }
                if(message!=null && message.contains("Product not found with ID")){
                    return new ProductNotFoundException(message);
                }

                return new RuntimeException(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return defaultDecoder.decode(methodKey, response);
    }
}