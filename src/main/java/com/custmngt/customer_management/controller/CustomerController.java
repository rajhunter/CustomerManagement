package com.custmngt.customer_management.controller;

import com.custmngt.customer_management.dto.ApiResponse;
import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.exception.CustomerNotFoundException;
import com.custmngt.customer_management.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.custmngt.customer_management.constants.MessageConstants.CUSTOMER_CREATED;
import static com.custmngt.customer_management.constants.MessageConstants.CUSTOMER_NOT_FOUND;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse response = customerService.createCustomer(customerRequest);
        ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>(CUSTOMER_CREATED, response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getCustomer/{uuid}")
    public Customer getCustomer(@PathVariable UUID uuid) {
        return customerService.getCustomerById(uuid)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND + uuid.toString()));
    }

}
