FROM openjdk:17-slim
ARG JAR_FILE=target/*.jar
EXPOSE 8080
# Install Python and gallery-dl
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    pip3 install gallery-dl
	
COPY target/downloader-0.0.1-SNAPSHOT.jar spring-app.jar
#COPY src/main/resources/gallery-dl.exe /gallery-dl.exe
ENTRYPOINT ["java","-jar","spring-app.jar"]