package com.eCcommerce.main.dto;


import com.eCcommerce.main.model.Address;
import com.eCcommerce.main.model.Item;
import com.eCcommerce.main.model.OrderStatus;
import com.eCcommerce.main.model.PaymentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;


    @NotEmpty(message = "Order must contain at least one item")
    private List<ItemDto> items;

    @NotNull(message = "Shipping amount is required")
    @PositiveOrZero(message = "Shipping amount must be zero or positive")
    private Double shippingAmount;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

    @NotNull(message = "Total amount is required")
    @PositiveOrZero(message = "Total amount can not be negative")
    private Double amountPaid;

    @NotNull(message = "Address is required")
    private Address address;
}
