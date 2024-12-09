package com.bank.employee.db.operation.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(final String message) {
        super(message);
    }
}
