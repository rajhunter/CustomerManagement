package com.custmngt.customer_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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
public class UpdateRequest {
    @NotBlank
    private String customerName;

    @Email
    private String emailId;

    @PositiveOrZero
    private Double annualSpend;

    private LocalDate lastPurchaseDate;
}
