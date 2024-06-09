FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/demo*.jar
RUN mkdir /configuration
COPY ${JAR_FILE} /demo.jar
ENTRYPOINT ["java","-jar","/demo.jar","--spring.config.additional-location=file:/configuration/"]