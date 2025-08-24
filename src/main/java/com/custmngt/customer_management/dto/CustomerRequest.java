package com.custmngt.customer_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    private UUID uuid;
    @NotBlank(message = "Customer name cannot be null or empty!")
    private String customerName;
    @NotBlank(message = "Email cannot be null or empty!")
    @Email(message = "Email format is not valid!")
    private String emailId;
    private Double annualSpend;
    private LocalDate lastPurchaseDate;
}
