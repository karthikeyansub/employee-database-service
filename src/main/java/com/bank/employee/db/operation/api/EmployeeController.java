package com.bank.employee.db.operation.api;

import com.bank.employee.db.operation.api.dto.EmployeeDto;
import com.bank.employee.db.operation.service.EmployeeService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//TODO: Yet to implement input validation, swagger doc, logger
@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployeeById(@PathParam("id") final Integer id) {
        log.info("Received request to get /employees/{}", id);

        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/employees")
    public EmployeeDto createEmployee(@RequestBody final EmployeeDto employeeDto) {

        return employeeService.createEmployee(employeeDto);
    }

    @PutMapping("/employees/{id}")
    public EmployeeDto updateEmployee(
            @PathParam("id") final int id,
            @RequestBody final EmployeeDto employeeDto) {
        return null;
    }

    @DeleteMapping("employees/{id}")
    public String deleteEmployee(@PathParam("id") final int id) {
        return null;
    }
}
