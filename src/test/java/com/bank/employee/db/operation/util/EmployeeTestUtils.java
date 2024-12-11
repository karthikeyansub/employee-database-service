package com.bank.employee.db.operation.util;

import java.util.Base64;

public class EmployeeTestUtils {

    public static String basicAuthHeaderValue() {
        return "Basic " + Base64.getEncoder().encodeToString("employee_app:password".getBytes());
    }
}
