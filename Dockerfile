# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-alpine
WORKDIR /app
ARG JAR_FILE=target/demo*.jar
RUN mkdir /configuration
COPY ${JAR_FILE} /demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/demo.jar","--spring.config.additional-location=file:/configuration/"]