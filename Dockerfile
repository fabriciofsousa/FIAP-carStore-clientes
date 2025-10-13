FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM openjdk:17-ea-17-jdk-slim-buster

WORKDIR /app

COPY --from=build /app/target/*.jar clienteApp.jar

EXPOSE 8080

#ENV SPRING_PROFILES_ACTIVE=dev

CMD ["java", "-jar", "clienteApp.jar"]