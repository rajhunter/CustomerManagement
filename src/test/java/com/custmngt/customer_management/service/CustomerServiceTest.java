package com.custmngt.customer_management.service;

import com.custmngt.customer_management.entity.Customer;
import com.custmngt.customer_management.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetCustomerByEmail_WhenCustomerExists() {
        Customer customer = new Customer(
                UUID.randomUUID(), "Raj Yadav", "raj@abc.com", 100.0, LocalDate.now()
        );
        List<Customer> customers = List.of(customer);
        when(customerRepository.findByEmailId("raj@abc.com")).thenReturn(customers);
        Optional<List<Customer>> result = customerService.getCustomerByEmail("raj@abc.com");
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("Raj Yadav", result.get().get(0).getCustomerName());
    }

    @Test
    void testGetCustomerByEmail_WhenCustomerNotFound() {
        when(customerRepository.findByEmailId("invalid@abc.com")).thenReturn(Collections.emptyList());
        Optional<List<Customer>> result = customerService.getCustomerByEmail("invalid@abc.com");
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCustomerByEmail_WhenRepositoryReturnsNull() {
        when(customerRepository.findByEmailId("raj@abc.com")).thenReturn(null);
        Optional<List<Customer>> result = customerService.getCustomerByEmail("raj@abc.com");
        assertTrue(result.isEmpty());
    }

}
