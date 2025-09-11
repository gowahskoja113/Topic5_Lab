package com.example.lab1.service;

import com.example.lab1.dto.OrderRequest;
import com.example.lab1.dto.OrderResponse;
import com.example.lab1.repository.OrderRepository;

import java.util.List;

public interface OrderService {
   List<OrderResponse> getAllOrder();
    OrderResponse getOrderById(int id);
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(int id, OrderRequest orderRequest);
    void deleteOrder(int id);
    List<OrderResponse> getOrdersByEmployeeId(Integer employeeId);
}
