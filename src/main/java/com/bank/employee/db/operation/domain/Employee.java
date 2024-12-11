package com.bank.employee.db.operation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "SURNAME")
    private String surname;

    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Integer roleId;
}
