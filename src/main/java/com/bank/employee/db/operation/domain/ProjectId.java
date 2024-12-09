package com.bank.employee.db.operation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@ToString
public final class ProjectId implements Serializable {

    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;

    @Column(name = "NAME", nullable = false)
    private String name;
}
