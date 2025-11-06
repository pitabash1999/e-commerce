package com.eCcommerce.main.service.interfaces;

import com.eCcommerce.main.dto.OrderRequestDto;
import com.eCcommerce.main.dto.OrderResponseDto;
import com.eCcommerce.main.exception.InsufficientStockException;

public interface OrderService {

    public OrderResponseDto saveOrder(OrderRequestDto orderRequest);
    public OrderResponseDto getOrderByOrderId(Long id);
    public OrderResponseDto cancelOrder(Long id);
}
