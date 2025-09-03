package com.custmngt.customer_management.utility;

import com.custmngt.customer_management.dto.CustomerRequest;
import com.custmngt.customer_management.dto.CustomerUpdateRequest;
import com.custmngt.customer_management.entity.Customer;

public class CustomerMapper {


    public static void updateCustomerFromRequest(Customer customer, CustomerUpdateRequest request) {
        if (request.getCustomerName() != null) {
            customer.setCustomerName(request.getCustomerName());
        }
        if (request.getEmailId() != null) {
            customer.setEmailId(request.getEmailId());
        }
//        if (request.getAnnualSpend() != null) {
//            customer.setAnnualSpend(request.getAnnualSpend());
//        }


    }
}
