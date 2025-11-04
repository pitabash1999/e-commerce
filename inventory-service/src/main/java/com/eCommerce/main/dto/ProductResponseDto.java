package com.eCommerce.main.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDto {

    private Long id;
    private String name;
    private String category;
    private Long quantity;
    private Double price;
    private LocalDateTime lastUpdatedAt;
}
