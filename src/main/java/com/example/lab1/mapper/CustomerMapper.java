package com.example.lab1.mapper;

import com.example.lab1.dto.CustomerRequest;
import com.example.lab1.dto.CustomerResponse;
import com.example.lab1.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setContactName(request.getContactName());
        customer.setCountry(request.getCountry());
        customer.setPostalCode(request.getPostalCode());
        return customer;
    }

    public static CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getCustomerId());
        response.setCustomerName(customer.getCustomerName());
        response.setCity(customer.getCity());
        response.setContactName(customer.getContactName());
        response.setCountry(customer.getCountry());
        response.setPostalCode(customer.getPostalCode());
        return response;
    }
}
