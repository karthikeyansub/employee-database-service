package com.bank.employee.db.operation.api;

import com.bank.employee.db.operation.domain.dto.RoleDto;
import com.bank.employee.db.operation.service.RoleCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class RoleController {

    private RoleCacheService roleCacheService;

    @Operation(summary = "Get roles",
            description = " This API will return roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns roles or empty if no roles found"),
            @ApiResponse(responseCode = "500", description = "System errors", content = @Content)
    })
    @GetMapping("/api/roles")
    public List<RoleDto> getEmployeeRoles() {
        log.info("Received request to get /api/roles");

        return roleCacheService.getRoles();
    }
}
