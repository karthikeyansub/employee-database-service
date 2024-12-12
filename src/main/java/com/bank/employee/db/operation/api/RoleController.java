package com.bank.employee.db.operation.api;

import com.bank.employee.db.operation.domain.dto.RoleReponse;
import com.bank.employee.db.operation.exception.ResourceNotFoundException;
import com.bank.employee.db.operation.service.RoleCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/api/roles/{roleName}")
    public RoleReponse getRoleByName(
            @Parameter(name = "Role", description = "Role name") @PathVariable("roleName") final String roleName) {
        log.info("Received request to get /api/roles");
        List<RoleReponse> roles = roleCacheService.getRoles();
        Optional<RoleReponse> role = roles.stream()
                .filter(r -> r.name().equalsIgnoreCase(roleName))
                .findFirst();

        if(role.isEmpty()) {
            throw new ResourceNotFoundException("Invalid role");
        }

        return role.get();
    }
}
