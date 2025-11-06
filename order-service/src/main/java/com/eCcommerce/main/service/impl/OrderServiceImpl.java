package com.eCcommerce.main.service.impl;

import com.eCcommerce.main.dto.*;
import com.eCcommerce.main.exception.InsufficientStockException;
import com.eCcommerce.main.exception.OrderNotFoundException;
import com.eCcommerce.main.exception.ProductNotFoundException;
import com.eCcommerce.main.external.PaymentService;
import com.eCcommerce.main.external.ProductService;
import com.eCcommerce.main.model.Item;
import com.eCcommerce.main.model.Order;
import com.eCcommerce.main.model.OrderStatus;
import com.eCcommerce.main.repository.OrderRepository;
import com.eCcommerce.main.service.interfaces.OrderService;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductService productService,
                            PaymentService paymentService,
                            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.paymentService=paymentService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public OrderResponseDto saveOrder(OrderRequestDto orderRequest)  {
        try{
            List<Item> items=productService.getItemsDetails(orderRequest.getItems());
            Order order=modelMapper.map(orderRequest,Order.class);
            order.setItems(items);
            for (Item item : items) {
                item.setOrder(order);
            }
            order.getAddress().setOrder(order);
            order=orderRepository.save(order);
            //transaction
            PaymentRequestDto paymentRequestDto=PaymentRequestDto.builder()
                    .amountPaid(orderRequest.getAmountPaid())
                    .totalAmount(orderRequest.getTotalAmount()+orderRequest.getShippingAmount())
                    .orderId(order.getOrderId())
                    .build();
            PaymentResponseDto paymentResponseDto=paymentService.savePayment(paymentRequestDto);
            OrderResponseDto orderResponseDto=modelMapper.map(order,OrderResponseDto.class);
            orderResponseDto.setPaymentDetails(paymentResponseDto);
            return orderResponseDto;
        }catch (ProductNotFoundException e){
            throw new ProductNotFoundException(e.getMessage());
        }catch (InsufficientStockException ex){
            throw new InsufficientStockException(ex.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderResponseDto getOrderByOrderId(Long id) {

        Order order=orderRepository.findByOrderId(id)
                .orElseThrow(()->new OrderNotFoundException("Order not found with this ID "+id));
        return modelMapper.map(order,OrderResponseDto.class);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(Long id) {

        Order order=orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundException("Order not found with this ID "+id));
        List<ItemDto> itemDtos=order.getItems().stream()
                                .map(item -> {
                                    return ItemDto.builder()
                                            .productId(item.getProductId())
                                            .orderedQuantity((long)item.getOrderedQuantity())
                                            .build();
                                })
                                        .toList();
        List<Item> items=productService.increaseStock(itemDtos);
        PaymentResponseDto paymentResponseDto=paymentService.refund(order.getOrderId());
        order.setOrderStatus(OrderStatus.CANCELLED);
        order=orderRepository.save(order);
        OrderResponseDto orderResponseDto=modelMapper.map(order,OrderResponseDto.class);
        orderResponseDto.setPaymentDetails(paymentResponseDto);
        return orderResponseDto;
    }
}
