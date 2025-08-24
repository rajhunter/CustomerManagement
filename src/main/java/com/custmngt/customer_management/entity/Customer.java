package com.custmngt.customer_management.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private UUID uuid;
    private String customerName;
    private String emailId;
    private Double annualSpend;
    private LocalDate lastPurchaseDate;



}
