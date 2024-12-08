package com.bank.employee.db.operation.repository;

import com.bank.employee.db.operation.domain.Project;
import com.bank.employee.db.operation.domain.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, ProjectId> {
}
