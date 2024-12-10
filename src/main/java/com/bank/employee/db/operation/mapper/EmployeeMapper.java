package com.bank.employee.db.operation.mapper;

import com.bank.employee.db.operation.domain.Employee;
import com.bank.employee.db.operation.domain.dto.EmployeeRequest;
import com.bank.employee.db.operation.domain.dto.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "name", expression = """
            java(employee.getFirstname() + " " + employee.getSurname())
        """)
    EmployeeResponse mapEmployeeToEmployeeResponse(final Employee employee);

    @Mapping(source = "name", target = "firstname", qualifiedByName = "getFirstName")
    @Mapping(source = "name", target = "surname", qualifiedByName = "getSurName")
    @Mapping(target = "id", ignore = true)
    Employee mapEmployeeRequestToEmployee(final EmployeeRequest employeeRequest);

    @Named("getFirstName")
    default String extractFirstName(String name) {
        return name != null ? name.split(" ")[0] : null;
    }

    @Named("getSurName")
    default String extractSurName(String name) {
        return name != null && name.split(" ").length > 0 ? name.split(" ")[1] : null;
    }
}
