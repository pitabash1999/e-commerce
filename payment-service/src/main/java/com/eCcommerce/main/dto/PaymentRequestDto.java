package com.eCcommerce.main.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequestDto {

    @NotNull(message = "amount paid is required")
    @PositiveOrZero(message = "Paid can not be negative")
    private Double amountPaid;
    @NotNull(message = "Total amount is required")
    @PositiveOrZero(message = "Total amount can not be negative")
    private Double totalAmount;
    @NotNull(message = "Order ID required")
    private Long orderId;
}
