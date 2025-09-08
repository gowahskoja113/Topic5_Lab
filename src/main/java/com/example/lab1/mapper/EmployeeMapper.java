package com.example.lab1.mapper;

import com.example.lab1.dto.EmployeeRequest;
import com.example.lab1.dto.EmployeeResponse;
import com.example.lab1.entity.Employee;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setBirthDate(request.getBirthDate());
        employee.setSupervisorId(request.getSupervisorId());
        return employee;
    }

    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setBirthDate(employee.getBirthDate());
        response.setSupervisorId(employee.getSupervisorId());
        return response;
    }
}
