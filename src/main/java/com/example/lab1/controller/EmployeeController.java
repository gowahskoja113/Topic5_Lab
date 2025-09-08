package com.example.lab1.controller;

import com.example.lab1.dto.EmployeeRequest;
import com.example.lab1.dto.EmployeeResponse;
import com.example.lab1.mapper.EmployeeMapper;
import com.example.lab1.repository.EmployeeRepository;
import com.example.lab1.entity.Employee;
import com.example.lab1.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable int id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return (employee != null)
                ? ResponseEntity.ok(employee)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse saved = employeeService.createEmployee(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable(value = "id") int id,
            @Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/supervisor/{supervisorId}")
    public ResponseEntity<List<EmployeeResponse>> getBySupervisor(@PathVariable Integer supervisorId) {
        return ResponseEntity.ok(employeeService.getBySupervisor(supervisorId));
    }
}
