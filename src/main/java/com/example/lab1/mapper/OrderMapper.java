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
    @Mapping(target = "customerId", source = "customer.customerId")
    OrderResponse toResponse(Order order);

    // RequestDTO -> Entity
    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "customer.customerId", source = "customerId")
    Order toEntity(OrderRequest orderRequest);
}

