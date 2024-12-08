package com.bank.employee.db.operation.service;

import com.bank.employee.db.operation.api.dto.EmployeeDto;
import com.bank.employee.db.operation.domain.Employee;
import com.bank.employee.db.operation.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeDto getEmployeeById(final int employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);

        //TODO: implement MapStruct for mapping
        return new EmployeeDto(employee.getId(),
                employee.getFirstName() + " " + employee.getSurName(),
                employee.getRoleId()
                );
    }

    //TODO: yet to implement
    public EmployeeDto createEmployee(final EmployeeDto employeeDto) {
        return null;
    }

    //TODO: yet to implement
    public EmployeeDto updateEmployee(final EmployeeDto employeeDto) {
        return null;
    }

    //TODO: yet to implement
    public String deleteEmployeeById(final Integer employeeId) {
        return null;
    }
}
