package com.bank.employee.db.operation.domain.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("basicauth.config")
@Data
public class UserAccount {

    private String username;
    private String password;
    private String role;
}
