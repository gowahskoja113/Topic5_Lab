package com.example.lab1.service;

import com.example.lab1.dto.OrderRequest;
import com.example.lab1.dto.OrderResponse;
import com.example.lab1.entity.Order;
import com.example.lab1.mapper.OrderMapper;
import com.example.lab1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(int id) {
        return orderRepository.findById(id)
                .map(orderMapper::toResponse)
                .orElse(null);
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(int id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setOrderDate(orderRequest.getOrderDate());
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getOrdersByEmployeeId(Integer employeeId) {
        return orderRepository.findByEmployeeId(employeeId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }
}
