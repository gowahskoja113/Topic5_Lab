package com.example.lab1.service;

import com.example.lab1.dto.CustomerRequest;
import com.example.lab1.dto.CustomerResponse;
import com.example.lab1.dto.EmployeeRequest;
import com.example.lab1.dto.EmployeeResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomer();
    CustomerResponse getCustomerById(int id);
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(int id, CustomerRequest request);
    void deleteCustomer(int id);
}