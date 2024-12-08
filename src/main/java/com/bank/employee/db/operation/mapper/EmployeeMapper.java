package com.bank.employee.db.operation.mapper;

import com.bank.employee.db.operation.api.dto.EmployeeDto;
import com.bank.employee.db.operation.domain.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "name", expression = "java(employee.getFirstname() + \" \" + employee.getSurname)")
    EmployeeDto mapEmployeeToEmployeeDto(final Employee employee);

    @Mapping(target = "firstName", expression = "java(employeeDto.getName() != null ? employeeDto.getName().split(\" \")[0] : null")
    @Mapping(target = "surName", expression = "java(employeeDto.getName() != null && employeeDto.getName().split(\" \").length > 1 ? employeeDto.getName().split(\" \")[1] : null")
    Employee mapEmployeeDtoToEmployee(final EmployeeDto employeeDto);
}
