FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
EXPOSE 8080

# Install Python and gallery-dl using apk
RUN apk update && \
    apk add --no-cache python3 py3-pip bash && \
    # Create a virtual environment and install gallery-dl inside it
    python3 -m venv /venv && \
    /venv/bin/pip install gallery-dl

# Copy the Spring Boot JAR file into the container
COPY target/downloader-0.0.1-SNAPSHOT.jar spring-app.jar

# Uncomment this line if you have a gallery-dl executable you want to copy
#COPY src/main/resources/gallery-dl.exe /gallery-dl.exe

# Set the virtual environment as the default for Python and pip
ENV PATH="/venv/bin:$PATH"

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "spring-app.jar"]
