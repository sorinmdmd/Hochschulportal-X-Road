# X-Road Example REST API

The X-Road Example REST API demonstrates an implementation of a REST API service that can be integrated into X-Road 7 as a service provider information system.

## Software Requirements

* Java 17 or later
* Docker (*optional*)

## Running the application locally

The fastest and easiest way to try out the application is by using the Spring Boot Gradle plugin. The only requirement
is to have Java 17 or later installed on your machine.

```bash
./gradlew bootRun
```

After that the application's OpenAPI specification becomes accessible at:

```
http://localhost:8080/v3/api-docs
```

And the Swagger UI becomes available at:

```
http://localhost:8080/swagger-ui/index.html
```

## Running the application inside a Docker container

You can create a Docker image to run the application inside a container, using the provided Dockerfile:

```bash
docker build -t example-restapi .
```

After the image is built, you can run the container:

```bash
docker run -p 8080:8080 example-restapi
```
