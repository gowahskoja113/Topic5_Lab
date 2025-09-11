package com.example.lab1.service;

import com.example.lab1.dto.CustomerRequest;
import com.example.lab1.dto.CustomerResponse;
import com.example.lab1.entity.Customer;
import com.example.lab1.mapper.CustomerMapper;
import com.example.lab1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(int id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::toResponse)
                .orElse(null);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.toEntity(request);
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse updateCustomer(int id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

            customer.setCustomerName(request.getCustomerName());
            customer.setAddress(request.getAddress());
            customer.setCity(request.getCity());
            customer.setContactName(request.getContactName());
            customer.setCountry(request.getCountry());
            customer.setPostalCode(request.getPostalCode());

            return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }
}
