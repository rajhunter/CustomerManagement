package com.custmngt.customer_management.repository;

import com.custmngt.customer_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByCustomerName(String customerName);
    List<Customer> findByEmailId(String emailId);

}
