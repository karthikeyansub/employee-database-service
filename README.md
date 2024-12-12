## Employee Database Service

It's an internal API which will be accessed with basic authorization.

# Requirement Assumption

* Many to One between Employee to Project, This means an employee can work on many project. And each project is assigned to one employee.
* Reassign the projects to the default employee after deleting the role and associated employees using procedure. Assuming to create new default employee with available role and assign him to the deleted employees associated projects.

# Run Instruction:

## Build and Test
    - Use 'mvc clean install' to build the application and run unit and spring boot integration test.

## Run application
    - Use 'java -jar .\employee-management-service-0.0.1.jar' from the jar file location.

## Test API using Swagger UI
    - Use swagger to test the API's - http://localhost:8080/swagger-ui/index.html.

## Test Result
![img.png](code_coverage.png)