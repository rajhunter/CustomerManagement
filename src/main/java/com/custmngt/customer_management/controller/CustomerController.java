package com.custmngt.customer_management.controller;

import com.custmngt.customer_management.dto.CustomApiResponse;
import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.dto.UpdateRequest;
import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.exception.CustomerNotFoundException;
import com.custmngt.customer_management.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.custmngt.customer_management.constants.MessageConstants.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Customer API", description = "Operations related to customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    @Operation(
            summary = "Create a new customer",
            description = "Creates a customer with the provided details and returns the created customer response.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Customer details to create",
                    content = @Content(
                            schema = @Schema(implementation = CustomerRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body"
                    )
            }
    )
    public ResponseEntity<CustomApiResponse<CustomerResponse>> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse response = customerService.createCustomer(customerRequest);
        CustomApiResponse<CustomerResponse> customApiResponse = new CustomApiResponse<>(CUSTOMER_CREATED, response);
        return ResponseEntity.ok(customApiResponse);
    }

    @PutMapping("/customers/{uuid}")
    public ResponseEntity<CustomApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable UUID uuid,
            @RequestBody @Valid UpdateRequest updateRequest) {
        CustomerResponse response = customerService.updateCustomer(uuid, updateRequest);
        CustomApiResponse<CustomerResponse> customApiResponse = new CustomApiResponse<>(CUSTOMER_UPDATED, response);
        return ResponseEntity.ok(customApiResponse);
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
    public ResponseEntity<CustomApiResponse<String>> deleteCustomer(@PathVariable UUID id) {
        String msg = customerService.deleteCustomerById(id);
        return ResponseEntity.ok(new CustomApiResponse<>(msg, null));
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
