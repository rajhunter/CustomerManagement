package com.custmngt.customer_management.service;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.repository.CustomerRepository;
import com.custmngt.customer_management.utility.CustomerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        Customer customer= Customer.builder()
                .customerName(customerRequest.getCustomerName())
                .emailId(customerRequest.getEmailId())
                .annualSpend(customerRequest.getAnnualSpend())
                .lastPurchaseDate(LocalDate.now())
                .build();
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerUtility.mapToResponse(savedCustomer);

    }

    @Override
    public Optional<Customer> getCustomerById(UUID uuid) {
        return customerRepository.findById(uuid);
    }
}
