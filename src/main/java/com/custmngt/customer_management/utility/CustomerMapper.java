package com.custmngt.customer_management.utility;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.UpdateRequest;
import com.custmngt.customer_management.entity.Customer;

import java.util.UUID;

public class CustomerMapper {


    public static void updateCustomerFromRequest(Customer customer, UpdateRequest request) {
        if (request.getCustomerName() != null) {
            customer.setCustomerName(request.getCustomerName());
        }

        if (request.getEmailId() != null) {
            customer.setEmailId(request.getEmailId());
        }
        if (request.getAnnualSpend() != null) {
            customer.setAnnualSpend(request.getAnnualSpend());
        }
        if (request.getLastPurchaseDate() != null) {
            customer.setLastPurchaseDate(request.getLastPurchaseDate());
        }

    }
}
