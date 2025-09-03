package com.custmngt.customer_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Data
public class Customer {

@Id
    @GeneratedValue
    @UuidGenerator
    private UUID uuid;
    @Column(nullable = false)
    private String customerName;
    @Email
    private String emailId;
    private Double annualSpend;
    private LocalDate lastPurchaseDate;



}
