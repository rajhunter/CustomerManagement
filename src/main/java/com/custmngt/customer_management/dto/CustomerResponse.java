package com.custmngt.customer_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private UUID uuid;
    private String customerName;
    private String emailId;
    private Double annualSpend;
    private LocalDate lastPurchaseDate;
    private MembershipTier teirType;

}
