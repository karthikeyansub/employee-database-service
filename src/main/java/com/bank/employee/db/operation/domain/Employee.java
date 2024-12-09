package com.bank.employee.db.operation.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Data
@NoArgsConstructor
public final class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Integer roleId;
}
