package com.eCcommerce.main.service.interfaces;

import com.eCcommerce.main.dto.PaymentRequestDto;
import com.eCcommerce.main.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto savePayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto refundPayment(Long orderId);
}
