package com.bank.employee.db.operation.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record EmployeeResponse(
        @NotNull(message = "The field 'id' should not be null") int id,
        @NotNull(message = "The field 'name' should not be null")String name,
        @NotNull(message = "The field 'role_id' should not be null") @JsonProperty("role_id") int roleId)
        implements Serializable {
}