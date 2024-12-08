## Employee Database Service

# Requirement Clarification

1) Many to One between Employee to Project, This means an employee can work on many project. And each project is assigned to one employee.
2) Reassign the projects to the default employee after deleting the role and associated employees using procedure. Assuming to create new default employee with available role and assign him to the deleted employees associated projects.

# Minor changes in the requirement
1) changed the database column names in the employee table to keep uniform field names.
2) 