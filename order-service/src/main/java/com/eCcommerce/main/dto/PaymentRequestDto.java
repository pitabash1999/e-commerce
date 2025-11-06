package com.eCcommerce.main.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentRequestDto {

    private Double amountPaid;
    private Double totalAmount;
    private Long orderId;
}
