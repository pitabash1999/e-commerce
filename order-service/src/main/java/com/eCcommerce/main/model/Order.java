package com.eCcommerce.main.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order", orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Item> items;
    private Double shippingAmount;
    private Double totalAmount;
    @OneToOne(mappedBy = "order",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private Address address;
    @UpdateTimestamp
    private LocalDateTime orderedAt;
}
