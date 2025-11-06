package com.eCcommerce.main.external;


import com.eCcommerce.main.config.FeignConfig;
import com.eCcommerce.main.dto.PaymentRequestDto;
import com.eCcommerce.main.dto.PaymentResponseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PAYMENT-SERVICE",configuration = FeignConfig.class)
public interface PaymentService {

    @PostMapping("/api/payment/save")
    public PaymentResponseDto savePayment(@RequestBody PaymentRequestDto paymentRequestDto);
    @PutMapping("/api/payment/refund/{orderId}")
    public PaymentResponseDto refund(
            @PathVariable
            @NotNull(message = "OrderId required")
            @Positive(message = "Order ID must be positive")
            Long orderId
    );
}
