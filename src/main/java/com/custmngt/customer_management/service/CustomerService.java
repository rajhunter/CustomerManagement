package com.custmngt.customer_management.service;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.entity.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    public CustomerResponse createCustomer(CustomerRequest customerRequest);
    public Optional<Customer> getCustomerById(UUID uuid);

}
