# --------------------
# Stage 1
# --------------------
    FROM maven:3.9.11-eclipse-temurin-21 AS stage
    WORKDIR /app
    
    # Copy maven files into the working directory and cache dependencies
    COPY pom.xml .
    COPY mvnw .
    COPY mvnw.cmd .
    COPY .mvn/ .mvn

    # make the mvnw and mvnw.cmd executable
    RUN chmod +x mvnw
    RUN chmod +x mvnw.cmd
    
    # This layer will be cached unless pom.xml changes
    RUN ./mvnw dependency:go-offline -B
    
    # Copy all the source code into the working directory and build JAR
    COPY src ./src
    RUN ./mvnw clean package -DskipTests
    
# --------------------
# Stage 2
# --------------------
    FROM openjdk:21-jdk-slim
    WORKDIR /app
    
    # Copy JAR from build stage
    COPY --from=stage /app/target/*.jar spendguide-v1.jar

    # expose a port no.
    EXPOSE 8082
    
    ENTRYPOINT [ "java", "-jar", "spendguide-v1.jar" ]