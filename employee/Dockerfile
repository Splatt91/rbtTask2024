# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and the source code to the container
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use an official OpenJDK 17 image as the base image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8082

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]