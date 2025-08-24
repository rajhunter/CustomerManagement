package com.custmngt.customer_management.controller;

import com.custmngt.customer_management.dto.ApiResponse;
import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.dto.UpdateRequest;
import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.exception.CustomerNotFoundException;
import com.custmngt.customer_management.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.custmngt.customer_management.constants.MessageConstants.*;

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

    @PutMapping("/customers/{uuid}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable UUID uuid,
            @RequestBody @Valid UpdateRequest updateRequest) {
        CustomerResponse response = customerService.updateCustomer(uuid, updateRequest);
        ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>(CUSTOMER_UPDATED, response);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/getCustomer/{uuid}")
    public Customer getCustomer(@PathVariable UUID uuid) {
        return customerService.getCustomerById(uuid)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND + uuid.toString()));
    }

    @GetMapping("/getCustomerByName/{customerName}")
    public List<Customer> getCustomerListByName(@PathVariable String customerName) {
        return customerService.getCustomerByName(customerName)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND + customerName));
    }

    @GetMapping("/getCustomerByEmailId/{emailId}")
    public List<Customer> getCustomerListByEmailId(@PathVariable String emailId) {
        return customerService.getCustomerByEmail(emailId)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND + emailId));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable UUID id) {
        String msg = customerService.deleteCustomerById(id);
        return ResponseEntity.ok(new ApiResponse<>(msg, null));
    }

    @GetMapping("/getCustomerAnnualSpendsByEmail/{emailId}")
    public CustomerResponse getCustomerAnnualSpendsByEmail(@PathVariable String emailId) {
        return customerService.getCustomerAnnualSpendsByEmail(emailId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + emailId));
    }

        @GetMapping("/getCustomerAnnualSpends/{searchType}")
        public CustomerResponse getCustomerAnnualSpends(@PathVariable String searchType) {
            return customerService.getCustomerAnnualSpends(searchType)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + searchType));
        }

    }
