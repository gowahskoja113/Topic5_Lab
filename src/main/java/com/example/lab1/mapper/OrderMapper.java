package com.example.lab1.mapper;

import com.example.lab1.dto.OrderRequest;
import com.example.lab1.dto.OrderResponse;
import com.example.lab1.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Entity -> ResponseDTO
    @Mapping(target = "employeeId", source = "employee.id")
    //orderResponse.setEmployeeId(order.getEmployee().getId())
    @Mapping(target = "customerId", source = "customer.customerId")
    //orderResponse.setCustomerId(order.getCustomer().getCustomerId())
    OrderResponse toResponse(Order order);

    // RequestDTO -> Entity
    @Mapping(target = "employee.id", source = "employeeId")
    //order.setEmployeeId(orderRequest.getEmployeeId())
    @Mapping(target = "customer.customerId", source = "customerId")
    //order.setCustomerId(orderRequest.getCustomerId())
    Order toEntity(OrderRequest orderRequest);
}

