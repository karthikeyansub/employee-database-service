FROM openjdk:17-jdk-alpine
COPY target/*.jar employee-database-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "employee-database-service.jar"]
