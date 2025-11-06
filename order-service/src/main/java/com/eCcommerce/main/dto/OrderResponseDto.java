package com.eCcommerce.main.dto;


import com.eCcommerce.main.model.Address;
import com.eCcommerce.main.model.Item;
import com.eCcommerce.main.model.OrderStatus;
import com.eCcommerce.main.model.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long orderId;
    private OrderStatus orderStatus;
    private PaymentResponseDto paymentDetails;
    private List<Item> items;
    private Double shippingAmount;
    private Double totalAmount;
    private Address address;
    private LocalDateTime orderedAt;

}
