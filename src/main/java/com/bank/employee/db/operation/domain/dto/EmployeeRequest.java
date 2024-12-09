package com.bank.employee.db.operation.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record EmployeeRequest(
        @NotNull(message = "The field 'name' should not be null")String name,
        @NotNull(message = "The field 'role_id' should not be null") @JsonProperty("role_id") Integer roleId)
        implements Serializable {
}
