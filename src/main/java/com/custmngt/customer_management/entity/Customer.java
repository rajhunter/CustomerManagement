package com.custmngt.customer_management.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

@Id
    @GeneratedValue
    @UuidGenerator
    private UUID uuid;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false, unique = true)
    private String emailId;
    private Double annualSpend;
    private LocalDate lastPurchaseDate;



}
