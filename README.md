# Employee Database Service

It's an internal API which will be accessed with basic authorization (Credentials: `employee_app:password`. This application run on port: `8081`. 

## Run Instruction:

### Build and Test
    - Use 'mvc clean install' to build the application and run unit and spring boot integration test.

### Run application
    - Use 'java -jar .\employee-database-service-0.0.1.jar' from the jar file location.

### H2 Database console
    - Use this link to access H2 database - http://localhost:8081/h2-console/. (Credential `admin:adminpwd`)

### Test API using Swagger UI
    - Use swagger to test the API's - http://localhost:8081/swagger-ui/index.html.

# Requirement Assumption

* Many to One between Employee to Project, This means an employee can work on many project. And each project is assigned to one employee.
* Reassign the projects to the default employee after deleting the role and associated employees using procedure. Assuming to create new default employee with available role and assign him to the deleted employees associated projects.

## Test Result
![img.png](code_coverage.png)