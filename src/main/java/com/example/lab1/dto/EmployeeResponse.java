package com.example.lab1.dto;

import java.time.LocalDate;

public class EmployeeResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Integer supervisorId;

    public EmployeeResponse(Integer id, String firstName, String lastName, LocalDate birthDate, Integer supervisorId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.supervisorId = supervisorId;
    }

    public EmployeeResponse() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }
}
