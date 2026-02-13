# Etapa 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Production
FROM eclipse-temurin:17-jre AS prod
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN groupadd -r spring && useradd -r -g spring spring
USER spring
EXPOSE 8082
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]