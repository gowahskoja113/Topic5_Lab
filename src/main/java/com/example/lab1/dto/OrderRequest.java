package com.example.lab1.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class OrderRequest {

    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;

    private Integer employeeId;
    private Integer customerId;

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
