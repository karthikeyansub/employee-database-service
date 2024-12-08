package com.bank.employee.db.operation.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PROJECT")
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode
public class Project {

    @EmbeddedId
    private ProjectId projectId;

    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Employee employee;
}
