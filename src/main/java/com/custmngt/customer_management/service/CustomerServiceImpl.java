package com.custmngt.customer_management.service;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import org.springframework.transaction.annotation.Transactional;

import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.exception.CustomerNotFoundException;
import com.custmngt.customer_management.repository.CustomerRepository;
import com.custmngt.customer_management.utility.CustomerMapper;
import com.custmngt.customer_management.utility.CustomerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.custmngt.customer_management.utility.CustomerUtility.annualCalculation;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;
    @Override
    @Transactional
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
    @Transactional(readOnly=true)
    public Optional<Customer> getCustomerById(UUID uuid) {
        return customerRepository.findById(uuid);
    }

    @Override
    @Transactional
    public String deleteCustomerById(UUID uuid) {
        if (!customerRepository.existsById(uuid)) {
            throw new CustomerNotFoundException("Customer not found for : " + uuid);
        }
        customerRepository.deleteById(uuid);
        return "Customer deleted successfully with id: " + uuid;
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<List<Customer>> getCustomerByName(String customerName) {
            List<Customer> customers = customerRepository.findByCustomerName(customerName);
            return customers.isEmpty() ? Optional.empty() : Optional.of(customers);
        }

    @Override
    @Transactional(readOnly=true)
    public Optional<CustomerResponse> getCustomerAnnualSpendsByEmail(String emailId) {
        List<Customer> customers = customerRepository.findByEmailId(emailId);
       return annualCalculation( customers, emailId);
    }
    @Override
    @Transactional(readOnly=true)
    public Optional<CustomerResponse> getCustomerAnnualSpends(String searchType) {
        List<Customer> customerList = Collections.emptyList();
        if (CustomerUtility.isValidEmail(searchType)) {
            customerList = customerRepository.findByEmailId(searchType);
        } else {
            customerList = customerRepository.findByCustomerName(searchType);
        }
        return annualCalculation( customerList, searchType);
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<List<Customer>> getCustomerByEmail(String emailId) {
        return Optional.ofNullable(customerRepository.findByEmailId(emailId))
                .filter(list -> !list.isEmpty());
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(UUID uuid, CustomerRequest request) {
        Customer customer = customerRepository.findById(uuid)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + request));
        CustomerMapper.updateCustomerFromRequest(customer,request);
        Customer updateCustomer = customerRepository.save(customer);
        return CustomerUtility.mapToResponse(updateCustomer);
    }

}
