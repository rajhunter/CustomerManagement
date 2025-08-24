package com.custmngt.customer_management.utility;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.dto.MembershipTier;
import com.custmngt.customer_management.entity.Customer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

import static com.custmngt.customer_management.constants.MessageConstants.*;


@Slf4j
public class CustomerUtility {

    public static CustomerResponse  mapToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        log.info("Customer getID: {} ", customer.getUuid());
        log.info("Customer Date: {} ", customer.getLastPurchaseDate());

        response.setUuid(customer.getUuid());
        response.setCustomerName(customer.getCustomerName());
        response.setEmailId(customer.getEmailId());
        response.setAnnualSpend(customer.getAnnualSpend());
        response.setLastPurchaseDate(customer.getLastPurchaseDate());
        MembershipTier tier = MembershipTier.fromAnnualSpend(customer.getAnnualSpend());
        response.setTeirType(tier);
        return response;
    }

    public static CustomerResponse  mapToResponse(Customer customer, CustomerRequest request) {
        CustomerResponse response = new CustomerResponse();
       // log.info("Customer getID: {} ", request.getUuid());

        customer.setCustomerName(request.getCustomerName());
        customer.setEmailId(request.getEmailId());
        customer.setAnnualSpend(request.getAnnualSpend());
        customer.setLastPurchaseDate(LocalDate.now());
        return response;
    }


    public static CustomerResponse  mapToResponse(Customer customer, MembershipTier tier, Double totalAnnualSpend) {
        CustomerResponse response = new CustomerResponse();
        log.info("Customer getID: {} ", customer.getUuid());
        log.info("Customer Date: {} ", customer.getLastPurchaseDate());
        response.setUuid(customer.getUuid());
        response.setCustomerName(customer.getCustomerName());
        response.setEmailId(customer.getEmailId());
        response.setAnnualSpend(totalAnnualSpend);
        response.setTeirType(tier);
        response.setLastPurchaseDate(customer.getLastPurchaseDate());
        return response;
    }


    public static Optional<CustomerResponse> annualCalculation(List<Customer> customers, String searchType) {
        List<Customer> customerList = Collections.emptyList();
        if (isValid(searchType)) {
            customerList = customers.stream()
                    .filter(c -> c.getEmailId().equalsIgnoreCase(searchType))
                    .toList();
        } else {
            customerList = customers.stream()
                    .filter(c -> c.getCustomerName().equalsIgnoreCase(searchType))
                    .toList();
        }
        double totalAnnualSpend = customerList.stream()
                .map(Customer::getAnnualSpend)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
        LocalDate lastPurchaseDate = customerList.stream()
                .map(Customer::getLastPurchaseDate)
                .max(LocalDate::compareTo)
                .orElse(null);
        String customerName = customerList.isEmpty() ? null : customerList.get(0).getCustomerName();
        Customer customer = customers.get(0);
        MembershipTier tier = MembershipTier.fromAnnualSpend(totalAnnualSpend);
        return Optional.of(mapToResponse(customer, tier, totalAnnualSpend));
    }

    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


}
