package com.eCcommerce.main.controller;


import com.eCcommerce.main.dto.OrderRequestDto;
import com.eCcommerce.main.dto.OrderResponseDto;
import com.eCcommerce.main.exception.InsufficientStockException;
import com.eCcommerce.main.service.interfaces.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrderResponseDto> createOrder (
            @RequestBody @Valid OrderRequestDto orderRequestDto
            ) {
        OrderResponseDto orderResponseDto=orderService.saveOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable @Positive(message = "ID must be positive") Long id
    ){

        OrderResponseDto orderResponseDto=orderService.getOrderByOrderId(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);

    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable @Positive(message = "Id must be positive") Long id){
        OrderResponseDto orderResponseDto=orderService.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
