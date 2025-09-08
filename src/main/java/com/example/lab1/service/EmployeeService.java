package com.example.lab1.service;

import com.example.lab1.dto.EmployeeRequest;
import com.example.lab1.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(int id);
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse updateEmployee(int id, EmployeeRequest request);
    void deleteEmployee(int id);
    List<EmployeeResponse> getBySupervisor(Integer supervisorId);
}
