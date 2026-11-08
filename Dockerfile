FROM node:22-alpine AS frontend
WORKDIR /web
COPY Frontend/package*.json ./
RUN npm ci
COPY Frontend/ ./
RUN npm run build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY pom.xml ./
COPY Backend/ Backend/
COPY --from=frontend /Backend/api-gateway/src/main/resources/static/ Backend/api-gateway/src/main/resources/static/
ARG MODULE
RUN mvn -B -ntp -pl Backend/${MODULE} -am package -DskipTests
RUN cp Backend/${MODULE}/target/${MODULE}-1.0.0-SNAPSHOT.jar /app.jar
FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S app && adduser -S app -G app
WORKDIR /app
COPY --from=build /app.jar app.jar
USER app
ENTRYPOINT ["java","-XX:MaxRAMPercentage=65","-jar","app.jar"]
