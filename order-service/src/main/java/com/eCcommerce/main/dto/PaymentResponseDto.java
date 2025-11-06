package com.eCcommerce.main.dto;

import com.eCcommerce.main.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponseDto {

    private Long id;
    private String transactionId;
    private PaymentStatus paymentStatus;
    private Double amountPaid;
    private Double totalAmount;
    private LocalDateTime paidAt;
}
