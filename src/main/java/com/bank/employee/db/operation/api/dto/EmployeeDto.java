package com.bank.employee.db.operation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeDto(
        int id,
        String name,
        @JsonProperty("role_id") int roleId) {
}
