FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY target/demo-0.0.1-SNAPSHOT.jar spring-app.jar
ENTRYPOINT ["java","-jar","spring-app.jar"]