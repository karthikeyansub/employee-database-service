package com.bank.employee.db.operation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record EmployeeDto(
        int id,
        String name,
        @JsonProperty("role_id") int roleId) implements Serializable {
}
