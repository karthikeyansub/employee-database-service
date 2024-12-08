package com.bank.employee.db.operation.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "ROLE")
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Role {

    @Id
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(targetEntity = Employee.class, fetch = FetchType.LAZY)
    private List<Employee> employees;
}
