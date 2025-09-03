package com.custmngt.customer_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerUpdateRequest {
    // private UUID uuid;
    @NotBlank(message = "Customer name cannot be null or empty!")
    private String customerName;
    @NotBlank(message = "Email cannot be null or empty!")
    @Email(message = "Email format is not valid!")
    private String emailId;
}
