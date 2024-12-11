package com.bank.employee.db.operation.api;

import com.bank.employee.db.operation.domain.dto.EmployeeRequest;
import com.bank.employee.db.operation.domain.dto.EmployeeResponse;
import com.bank.employee.db.operation.domain.dto.ApiErrorResponse;
import com.bank.employee.db.operation.util.EmployeeTestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@Sql({"/data-integration-test.sql"})
class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String EMPLOYEE_API_URI = "/api/employees";

    @Nested
    @DisplayName("Happy Flow")
    class HappyFlow {

        @Test
        @DisplayName("GET:/api/employees/{id} - API will return the 200 response with employee response")
        void testGetEmployeeById_WillReturnEmployee() throws Exception {

            MvcResult result = mockMvc.perform(get(EMPLOYEE_API_URI + "/1")
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            EmployeeResponse response = objectMapper.readValue(responseJson, EmployeeResponse.class);
            assertEquals("John Wick", response.name());
            assertEquals(3, response.roleId());
        }

        @Test
        @DisplayName("POST:/api/employees - API will return the 200 response with employee response")
        void testCreateEmployee_WillReturnEmployee() throws Exception {
            EmployeeRequest request = new EmployeeRequest("Tom Hanks", 2);
            MvcResult result = mockMvc.perform(post(EMPLOYEE_API_URI)
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(covertToJsonString(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            EmployeeResponse response = objectMapper.readValue(responseJson, EmployeeResponse.class);
            assertEquals("Tom Hanks", response.name());
            assertEquals(2, response.roleId());
        }

        @Test
        @DisplayName("POST:/api/employees - API will return the 200 response with employee response (without surname)")
        void testCreateEmployee_WithoutSurname_WillReturnEmployee() throws Exception {
            EmployeeRequest request = new EmployeeRequest("Tom", 2);
            MvcResult result = mockMvc.perform(post(EMPLOYEE_API_URI)
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(covertToJsonString(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            EmployeeResponse response = objectMapper.readValue(responseJson, EmployeeResponse.class);
            assertEquals("Tom", response.name());
            assertEquals(2, response.roleId());
        }

        @Test
        @DisplayName("PUT:/api/employees/{id} - API will return the 200 response with updated employee response")
        void testUpdateEmployee_WillReturnEmployee() throws Exception {
            EmployeeRequest request = new EmployeeRequest("John Corner", 3);
            MvcResult result = mockMvc.perform(put(EMPLOYEE_API_URI + "/1")
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(covertToJsonString(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            EmployeeResponse response = objectMapper.readValue(responseJson, EmployeeResponse.class);
            assertEquals("John Corner", response.name());
            assertEquals(3, response.roleId());
        }

        @Test
        @DisplayName("DELETE:/api/employees/{id} - API will return the 200 response with deleted success message")
        void testDeleteEmployee_WillReturnSuccessMessage() throws Exception {
            MvcResult result = mockMvc.perform(delete(EMPLOYEE_API_URI + "/10")
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string("Employee deleted successfully"))
                    .andReturn();
        }
    }

    @Nested
    @DisplayName("Error Flow")
    class ErrorFlow {

        @Test
        @DisplayName("GET:/api/employees/{id} - API will return the 404 - NOT FOUND response")
        void testGetEmployeeById_WillNotFoundResponse_IfEmployeeNotPresent() throws Exception {

            MvcResult result = mockMvc.perform(get(EMPLOYEE_API_URI + "/100")
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue()))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            ApiErrorResponse response = objectMapper.readValue(responseJson, ApiErrorResponse.class);
            assertEquals("NOT_FOUND", response.errorTitle());
            assertEquals("Employee not found", response.errorMessage());
        }

        @Test
        @DisplayName("POST:/api/employees - API will return the 400 response if employee name and/or role_id is null")
        void testCreateEmployee_WillReturnBadRequest_IfEmployeeNameIsNull() throws Exception {
            EmployeeRequest request = new EmployeeRequest(null, null);
            MvcResult result = mockMvc.perform(post(EMPLOYEE_API_URI)
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(covertToJsonString(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            ApiErrorResponse response = objectMapper.readValue(responseJson, ApiErrorResponse.class);
            assertEquals("BAD_REQUEST", response.errorTitle());
            assertEquals("Input validation error", response.errorMessage());
            assertTrue(response.errorDetails().contains("The field 'name' should not be null"));
            assertTrue(response.errorDetails().contains("The field 'role_id' should not be null"));
        }

        @Test
        @DisplayName("POST:/api/employees - API will return the 400 response if employee name is empty")
        void testCreateEmployee_WillReturnBadRequest_IfRoleIsNull() throws Exception {
            EmployeeRequest request = new EmployeeRequest("", 2);
            MvcResult result = mockMvc.perform(post(EMPLOYEE_API_URI)
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(covertToJsonString(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            ApiErrorResponse response = objectMapper.readValue(responseJson, ApiErrorResponse.class);
            assertEquals("BAD_REQUEST", response.errorTitle());
            assertEquals("Input validation error", response.errorMessage());
            assertTrue(response.errorDetails().contains("The field 'name' should not be empty"));
        }

        @Test
        @DisplayName("POST:/api/employees - API will return the 500 response if any other errors")
        void testCreateEmployee_WillReturnInternalServerError_IfAnyOtherErrors() throws Exception {
            EmployeeRequest request = new EmployeeRequest("Tom Hanks", 7);
            MvcResult result = mockMvc.perform(post(EMPLOYEE_API_URI)
                            .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(covertToJsonString(request)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isInternalServerError())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            ApiErrorResponse response = objectMapper.readValue(responseJson, ApiErrorResponse.class);
            assertEquals("INTERNAL_SERVER_ERROR", response.errorTitle());
            assertEquals("Something went wrong", response.errorMessage());
        }
    }

    private String covertToJsonString(final EmployeeRequest request) throws JsonProcessingException {
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(request);
    }

}