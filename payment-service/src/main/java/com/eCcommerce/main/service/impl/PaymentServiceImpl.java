package com.eCcommerce.main.service.impl;

import com.eCcommerce.main.dto.PaymentRequestDto;
import com.eCcommerce.main.dto.PaymentResponseDto;
import com.eCcommerce.main.exception.ExcessPaymentException;
import com.eCcommerce.main.exception.OrderNotFoundException;
import com.eCcommerce.main.model.PaymentStatus;
import com.eCcommerce.main.model.Transaction;
import com.eCcommerce.main.repository.PaymentRepository;
import com.eCcommerce.main.service.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${transaction.idGeneratorString}")
    private String transactionIdGenerator;

    @Override
    public PaymentResponseDto savePayment(PaymentRequestDto paymentRequestDto) {

        PaymentStatus paymentStatus=null;
        if(paymentRequestDto.getAmountPaid().equals(paymentRequestDto.getTotalAmount())){
            paymentStatus=PaymentStatus.PAID;
        } else if (paymentRequestDto.getAmountPaid()>0) {
            paymentStatus=PaymentStatus.PARTIAL;
        }else{
            paymentStatus=PaymentStatus.UNPAID;
        }

        String transactionId= getTransactionId();
        Transaction transaction=new Transaction();
        transaction.setAmountPaid(paymentRequestDto.getAmountPaid());
        transaction.setTotalAmount(paymentRequestDto.getTotalAmount());
        transaction.setPaymentStatus(paymentStatus);
        transaction.setOrderId(paymentRequestDto.getOrderId());
        transaction.setTransactionId(transactionId);
        transaction=paymentRepository.save(transaction);

        return modelMapper.map(transaction,PaymentResponseDto.class);

    }

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {

        Transaction transaction=paymentRepository.findByOrderId(paymentRequestDto.getOrderId())
                .orElseThrow(()->new OrderNotFoundException("Order not found with ID "+paymentRequestDto.getOrderId()));
        if(transaction.getPaymentStatus().equals(PaymentStatus.REFUND)){
            throw new OrderNotFoundException("Order cancelled.");
        }
        double total=paymentRequestDto.getAmountPaid()+transaction.getAmountPaid();
        if(total>transaction.getTotalAmount()){
            throw new ExcessPaymentException("You have given more than expected");
        }
        if(total< transaction.getTotalAmount()){
            transaction.setAmountPaid(total);
            return modelMapper.map(paymentRepository.save(transaction),PaymentResponseDto.class);
        }

        transaction.setAmountPaid(total);
        transaction.setPaymentStatus(PaymentStatus.PAID);

        return modelMapper.map(paymentRepository.save(transaction),PaymentResponseDto.class);
    }

    @Override
    @Transactional
    public PaymentResponseDto refundPayment(Long orderId) {
        Transaction transaction = paymentRepository.findByOrderId(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order not found with orderID "+orderId));
        transaction.setPaymentStatus(PaymentStatus.REFUND);
        transaction=paymentRepository.save(transaction);
        return modelMapper.map(transaction,PaymentResponseDto.class);
    }

    private String getTransactionId(){
        StringBuilder stringBuilder = new StringBuilder();
        Random random=new Random();
        for(int i=0;i<15;i++){
            stringBuilder.append(transactionIdGenerator.charAt(random.nextInt(transactionIdGenerator.length()-1)));
        }
        return stringBuilder.toString();
    }

}
