package com.custmngt.customer_management.utility;

import com.custmngt.customer_management.dto.CustomerResponse;
import com.custmngt.customer_management.entity.Customer;
import lombok.extern.slf4j.Slf4j;

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
        return response;
    }

}
