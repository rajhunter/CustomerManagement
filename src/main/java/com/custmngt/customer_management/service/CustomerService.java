package com.custmngt.customer_management.service;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    public CustomerResponse createCustomer(CustomerRequest customerRequest);
    public Optional<Customer> getCustomerById(UUID uuid);
    public String deleteCustomerById(UUID uuid);

    public Optional<List<Customer>> getCustomerByName(String customerName);
    public Optional<List<Customer>> getCustomerByEmail(String emailId);
    public CustomerResponse updateCustomer(UUID uuid, CustomerRequest updateRequest);
    public Optional<CustomerResponse> getCustomerAnnualSpendsByEmail(String emailId);

    public Optional<CustomerResponse> getCustomerAnnualSpends(String searchType) ;

}
