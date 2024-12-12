package com.bank.employee.db.operation.service;

import com.bank.employee.db.operation.domain.Employee;
import com.bank.employee.db.operation.domain.dto.ApiSuccessResponse;
import com.bank.employee.db.operation.domain.dto.EmployeeRequest;
import com.bank.employee.db.operation.domain.dto.EmployeeResponse;
import com.bank.employee.db.operation.exception.ResourceNotFoundException;
import com.bank.employee.db.operation.mapper.EmployeeMapper;
import com.bank.employee.db.operation.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    public EmployeeResponse getEmployeeById(final int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        log.debug("Retrieved employee details - {}", employee);

        return employeeMapper.mapEmployeeToEmployeeResponse(employee);
    }

    @Transactional
    public EmployeeResponse createEmployee(final EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.mapEmployeeRequestToEmployee(employeeRequest);
        log.debug("Employee details to be saved - {}", employee);

        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        log.debug("Saved employee details - {}", savedEmployee);

        return employeeMapper.mapEmployeeToEmployeeResponse(savedEmployee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(final int employeeId, final EmployeeRequest employeeRequest) {
        Employee employee = findEmployeeById(employeeId);
        log.debug("Retrieved employee details to update employee - {}", employee);

        employee.setFirstname(employeeMapper.extractFirstName(employeeRequest.name()));
        employee.setSurname(employeeMapper.extractSurName(employeeRequest.name()));
        employee.setRoleId(employeeRequest.roleId());

        Employee updatedEmployee = employeeRepository.saveAndFlush(employee);
        log.debug("Updated employee details - {}", updatedEmployee);

        return employeeMapper.mapEmployeeToEmployeeResponse(updatedEmployee);
    }

    public ApiSuccessResponse deleteEmployeeById(final Integer employeeId) {
        Employee employee = findEmployeeById(employeeId);
        log.debug("Retrieved employee details to delete an employee - {}", employee);

        employeeRepository.delete(employee);
        log.debug("Employee(Id: {}) deleted successfully.", employeeId);

        return new ApiSuccessResponse("Employee deleted successfully");
    }

    private Employee findEmployeeById(final Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return employee.get();
    }
}
