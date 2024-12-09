package com.bank.employee.db.operation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ROLE")
@Data
@EqualsAndHashCode
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Role implements Serializable {

    @Id
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(targetEntity = Employee.class, fetch = FetchType.LAZY)
    private List<Employee> employees;
}
