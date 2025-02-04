package com.bank.employee.db.operation.config;

import com.bank.employee.db.operation.domain.security.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(UserAccount.class)
@AllArgsConstructor
public class WebSecurityConfig {

    private static final String APP_ADMIN = "APP_ADMIN";

    private final UserAccount userAccount;

    @Bean
    public SecurityFilterChain securityFilterChainForTest(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests -> requests
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/employees/**")).hasRole(APP_ADMIN)
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/employees")).hasRole(APP_ADMIN)
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/employees/**")).hasRole(APP_ADMIN)
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/employees/**")).hasRole(APP_ADMIN)
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/roles/**")).hasRole(APP_ADMIN)
                        .requestMatchers(antMatcher(HttpMethod.GET, "/actuator/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.OPTIONS, "/**")).permitAll()
                        .anyRequest().denyAll())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpSecurityHttpBasicConfigurer ->
                        httpSecurityHttpBasicConfigurer.realmName("Basic"))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(userAccount.getUsername())
                .password(bCryptPasswordEncoder.encode(userAccount.getPassword()))
                .roles(userAccount.getRole()).build());
        return manager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}