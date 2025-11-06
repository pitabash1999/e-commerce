package com.eCcommerce.main.controller;

import com.eCcommerce.main.dto.PaymentRequestDto;
import com.eCcommerce.main.dto.PaymentResponseDto;
import com.eCcommerce.main.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<PaymentResponseDto> savePayment(
            @RequestBody @Valid PaymentRequestDto paymentRequestDto
            ){
        PaymentResponseDto paymentResponseDto=paymentService.savePayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDto);
    }

    @PatchMapping("/make-payment")
    public ResponseEntity<PaymentResponseDto> makePayment(
            @RequestBody @Valid PaymentRequestDto paymentRequestDto
    ){
        PaymentResponseDto paymentResponseDto=paymentService.makePayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDto);
    }

    @PutMapping("/refund/{orderId}")
    public ResponseEntity<PaymentResponseDto> refund(
            @PathVariable
            @NotNull(message = "OrderId required")
            @Positive(message = "Order ID must be positive")
            Long orderId
    ){
        PaymentResponseDto paymentResponseDto=paymentService.refundPayment(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDto);
    }
}
