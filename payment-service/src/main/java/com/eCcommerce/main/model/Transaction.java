package com.eCcommerce.main.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Indexed;

import java.time.LocalDateTime;

@Entity(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "transactions", indexes = {
        @Index(name = "idx_order_id", columnList = "orderId")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String transactionId;
    @Column(unique = true,nullable = false)
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Double totalAmount;
    private Double amountPaid;
    @UpdateTimestamp
    private LocalDateTime paidAt;
}
