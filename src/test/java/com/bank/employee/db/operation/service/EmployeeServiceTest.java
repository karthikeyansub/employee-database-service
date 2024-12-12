package com.bank.employee.db.operation.service;

import com.bank.employee.db.operation.domain.Employee;
import com.bank.employee.db.operation.domain.dto.EmployeeRequest;
import com.bank.employee.db.operation.domain.dto.EmployeeResponse;
import com.bank.employee.db.operation.exception.ResourceNotFoundException;
import com.bank.employee.db.operation.mapper.EmployeeMapper;
import com.bank.employee.db.operation.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    private EmployeeService classUnderTest;

    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Mock
    private EmployeeMapper mockEmployeeMapper;

    @BeforeEach
    void setUp() {
        classUnderTest = new EmployeeService(mockEmployeeRepository, mockEmployeeMapper);
    }

    @Nested
    @DisplayName("Happy FLow")
    class HappyFlow {

        @Test
        void testGetEmployeeById_ShouldReturnEmployee() {
            when(mockEmployeeRepository.findById(anyInt())).thenReturn(getEmployee());
            when(mockEmployeeMapper.mapEmployeeToEmployeeResponse(any(Employee.class)))
                    .thenReturn(getEmployeeResponse());

            EmployeeResponse response = classUnderTest.getEmployeeById(1);

            assertNotNull(response);
            assertEquals("John Wick", response.name());
            assertEquals(1, response.roleId());
        }

        @Test
        void testCreateEmployee_ShouldReturnSavedEmployee() {
            when(mockEmployeeMapper.mapEmployeeRequestToEmployee(any(EmployeeRequest.class)))
                    .thenReturn(getEmployee().get());
            when(mockEmployeeRepository.saveAndFlush(any(Employee.class)))
                    .thenAnswer(result -> result.getArguments()[0]);
            when(mockEmployeeMapper.mapEmployeeToEmployeeResponse(any(Employee.class)))
                    .thenReturn(getEmployeeResponse());

            EmployeeResponse response = classUnderTest.createEmployee(getEmployeeRequestForCreate());

            assertNotNull(response);
            assertEquals("John Wick", response.name());
            assertEquals(1, response.roleId());
        }

        @Test
        void testUpdateEmployee_ShouldReturnUpdatedEmployee() {
            when(mockEmployeeRepository.findById(anyInt())).thenReturn(getEmployee());
            when(mockEmployeeRepository.saveAndFlush(any(Employee.class)))
                    .thenAnswer(result -> result.getArguments()[0]);
            when(mockEmployeeMapper.mapEmployeeToEmployeeResponse(any(Employee.class)))
                    .thenReturn(getEmployeeResponseForUpdate());

            EmployeeResponse response = classUnderTest.updateEmployee(1, getEmployeeRequestForUpdate());

            assertNotNull(response);
            assertEquals("John Conner", response.name());
            assertEquals(1, response.roleId());
        }

        @Test
        void testDeleteEmployeeById_ShouldDeleteEmployeeAndReturnMessage() {
            when(mockEmployeeRepository.findById(anyInt())).thenReturn(getEmployee());

            doNothing().when(mockEmployeeRepository).deleteById(anyInt());

            String response = classUnderTest.deleteEmployeeById(1);

            assertEquals("Employee deleted successfully", response);
        }
    }

    @Nested
    @DisplayName("Error FLow")
    class ErrorFlow {

        @Test
        void testGetEmployeeById_ShouldThrowEmployeeNotFoundException() {
            when(mockEmployeeRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> classUnderTest.getEmployeeById(10));
        }

        @Test
        void testUpdateEmployee_ShouldThrowEmployeeNotFoundException() {
            when(mockEmployeeRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> classUnderTest.updateEmployee(10, getEmployeeRequestForUpdate()));

            verify(mockEmployeeRepository, times(0)).saveAndFlush(getEmployee().get());
        }
    }

    private Optional<Employee> getEmployee() {
        return Optional.of(Employee.builder()
                .id(1)
                .firstname("John")
                .surname("Wick")
                .roleId(1)
                .build());
    }

    private EmployeeRequest getEmployeeRequestForCreate() {
        return new EmployeeRequest("John Wick", 1);
    }

    private EmployeeRequest getEmployeeRequestForUpdate() {
        return new EmployeeRequest("John Conner", 1);
    }

    private EmployeeResponse getEmployeeResponse() {
        return new EmployeeResponse(1, "John Wick", 1);
    }

    private EmployeeResponse getEmployeeResponseForUpdate() {
        return new EmployeeResponse(1, "John Conner", 1);
    }
}