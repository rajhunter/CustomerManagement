package com.custmngt.customer_management.service;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.dto.MembershipTier;
import com.custmngt.customer_management.dto.UpdateRequest;
import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.exception.CustomerNotFoundException;
import com.custmngt.customer_management.repository.CustomerRepository;
import com.custmngt.customer_management.utility.CustomerMapper;
import com.custmngt.customer_management.utility.CustomerUtility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.custmngt.customer_management.utility.CustomerUtility.annualCalculation;

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

    @Override
    @Transactional
    public String deleteCustomerById(UUID uuid) {
        if (!customerRepository.existsById(uuid)) {
            throw new CustomerNotFoundException("Customer not found with id: " + uuid);
        }
        customerRepository.deleteById(uuid);
        return "Customer deleted successfully with id: " + uuid;
    }

    @Override
    public Optional<List<Customer>> getCustomerByName(String customerName) {
            List<Customer> customers = customerRepository.findByCustomerName(customerName);
            return customers.isEmpty() ? Optional.empty() : Optional.of(customers);
        }

    @Override
    public Optional<CustomerResponse> getCustomerAnnualSpendsByEmail(String emailId) {
        List<Customer> customers = customerRepository.findByEmailId(emailId);
       return annualCalculation( customers, emailId);
    }
    @Override
    public Optional<CustomerResponse> getCustomerAnnualSpends(String searchType) {
        List<Customer> customers = customerRepository.findByEmailId(searchType);
        return annualCalculation( customers, searchType);
    }


    @Override
    public Optional<List<Customer>> getCustomerByEmail(String emailId) {
        return Optional.ofNullable(customerRepository.findByEmailId(emailId));
    }

    public CustomerResponse updateCustomer(UUID uuid, UpdateRequest request) {
        Customer customer = customerRepository.findById(uuid)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + request));
        CustomerMapper.updateCustomerFromRequest(customer,request);
        Customer updateCustomer = customerRepository.save(customer);
        return CustomerUtility.mapToResponse(updateCustomer);
    }


}
