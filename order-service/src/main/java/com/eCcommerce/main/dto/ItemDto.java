package com.eCcommerce.main.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemDto {

    private Long productId;
    private Long orderedQuantity;
}
