# Stage 1: Build stage with JDK image as builder
FROM openjdk:21-jdk-slim as builder

# Argument to specify the JAR file to copy
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the builder stage
COPY ${JAR_FILE} application.jar


# Run the JAR in tools mode to extract dependencies
RUN java -Djarmode=layertools -jar application.jar extract

# Stage 2: Runtime stage with JDK image
FROM openjdk:21-jdk-slim

# Copy dependencies, snapshot-dependencies, spring-boot-loader, and application JAR from builder stage
COPY --from=builder /dependencies/ ./
COPY --from=builder /snapshot-dependencies/ ./
COPY --from=builder /spring-boot-loader/ ./
COPY --from=builder /application/ ./

# Expose the port that your application runs on
EXPOSE 8080

# Specify the command to run your application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]