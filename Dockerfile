FROM eclipse-temurin:17-jre-alpine

ARG JAR_FILE=target/*.jar
EXPOSE 8080

# Install Python and gallery-dl on Alpine, bypassing system package restrictions
RUN apk add --no-cache python3 py3-pip && \
    pip3 install --no-cache-dir --break-system-packages gallery-dl

COPY target/downloader-0.0.1-SNAPSHOT.jar spring-app.jar

ENTRYPOINT ["java","-jar","spring-app.jar"]