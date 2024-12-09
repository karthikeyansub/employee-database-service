package com.bank.employee.db.operation.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String errorTitle,
                            String errorMessage,
                            List<String> errorDetails) {
}
