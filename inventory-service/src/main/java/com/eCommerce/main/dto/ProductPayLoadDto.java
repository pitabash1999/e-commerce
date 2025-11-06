package com.eCommerce.main.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductPayLoadDto {

    private Long productId;
    private String name;
    private String category;
    private Double price;
    private Long orderedQuantity;
}
