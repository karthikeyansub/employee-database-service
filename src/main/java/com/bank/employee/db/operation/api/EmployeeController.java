package com.bank.employee.db.operation.api;

import com.bank.employee.db.operation.domain.dto.ApiSuccessResponse;
import com.bank.employee.db.operation.domain.dto.EmployeeRequest;
import com.bank.employee.db.operation.domain.dto.EmployeeResponse;
import com.bank.employee.db.operation.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/employees")
@AllArgsConstructor
@Validated
public class EmployeeController {

    private EmployeeService employeeService;

    @Operation(summary = "Get employee details by id",
            description = " This API will return employee details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns employee details"),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "System errors", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeResponse getEmployeeById(
            @Parameter(name = "id", description = "Employee Id") @PathVariable("id") final Integer employeeId) {
        log.info("Received request to get /employees/{}", employeeId);

        return employeeService.getEmployeeById(employeeId);
    }

    @Operation(summary = "Create new employee",
            description = " This API will create new employee and return employee details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns employee details"),
            @ApiResponse(responseCode = "500", description = "System errors", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeResponse createEmployee(@Valid @RequestBody final EmployeeRequest employeeRequest) {
        log.info("Received request to create /employees");

        return employeeService.createEmployee(employeeRequest);
    }

    @Operation(summary = "Update employee",
            description = " This API will update employee and return employee details with the updated information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns employee details"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "System errors", content = @Content)
    })
    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(
            @Parameter(name = "id", description = "Employee Id") @PathVariable("id") final Integer employeeId,
            @Valid @RequestBody final EmployeeRequest employeeRequest) {
        log.info("Received request to update /employees/{}", employeeId);

        return employeeService.updateEmployee(employeeId, employeeRequest);
    }

    @Operation(summary = "Delete employee",
            description = " This API will delete employee and return message in the response.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns employee deleted message"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "System errors", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ApiSuccessResponse deleteEmployee(
            @Parameter(name = "id", description = "Employee Id") @PathVariable("id") final Integer employeeId) {
        log.info("Received request to delete /employees/{}", employeeId);

        return employeeService.deleteEmployeeById(employeeId);
    }
}
