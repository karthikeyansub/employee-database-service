package com.bank.employee.db.operation.api;

import com.bank.employee.db.operation.domain.dto.RoleReponse;
import com.bank.employee.db.operation.util.EmployeeTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class RoleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET:/api/roles - API will return the 200 response with all roles in response")
    void testGetRoles_WillReturnAllRoles() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/roles")
                        .header(HttpHeaders.AUTHORIZATION, EmployeeTestUtils.basicAuthHeaderValue()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<RoleReponse> response = objectMapper.readValue(responseJson, List.class);
        assertEquals(3, response.size());
    }
}