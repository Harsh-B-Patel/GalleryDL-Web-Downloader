# GalleryDL Web Dowloader

A container front end for Gallry-DL CLI to download Playlists, pictures and videos.

**Stack:** Spring, Java,  Docker 
**Docker Image:** [DockerHub Image](https://hub.docker.com/r/harshbpatel/downloader-app)


## Key product features
- Uses Gallery-Dl project to downlaod list of playlists, pictures or Videos.
- Added functionality for list of URLs to download.
- Created a front end to easily add URLs. 

---
## Running the project on localhost
> **Prerequisites:**  
> Install Java, Maven, Python and Docker on your local machine.


### Docker Instructions
1) Install Docker Desktop. 
2) Run the command below. 
```
sudo docker run -d -p 8082:8080 harshbpatel/downloader-app
```
OR
run the dockercompose file. 

```

services:
  downloader:
    image: harshbpatel/downloader-app:latest
    ports:
      - "8082:8080"  # Map container port 8080 to host port 8082
    volumes:
      - ./downloads:/gallery-dl # Optional: Bind the target directory 
    restart: unless-stopped


docker compose up
```


### Setup Instructions
1) Clone and fork the repository and install all dependencies (Java, Maven, Python, Docker).
2) Create a local instance of MySQL server.
3) Set the MySQL variable in Constants Class.
4) Run below Maven Command to create jar for the Spring app.
```
mvn clean install
```
6) Either Compile and run Java Project from a Java IDE or run below Docker commands to create a docker image.
```
docker build -t downloader-app:latest .

docker run -p 8082:8080 downloader-app:latest
```
