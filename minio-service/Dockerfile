FROM gradle:8.5.0-jdk17-alpine AS build
VOLUME /tmp
ARG APP_HOME=/minio-service/
RUN mkdir $APP_HOME
WORKDIR $APP_HOME
ARG JAR_FILE=build/libs/educplatform-1.0.0.jar
COPY build.gradle settings.gradle gradle.properties $APP_HOME
COPY src src
RUN gradle clean build

FROM openjdk:17-alpine3.12
COPY --from=build /minio-service/build/libs/minio-service-1.0.0.jar minio-service.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/minio-service.jar"]