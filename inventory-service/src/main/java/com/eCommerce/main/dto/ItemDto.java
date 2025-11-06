package com.eCommerce.main.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemDto {

    private Long productId;
    private Long orderedQuantity;
}
