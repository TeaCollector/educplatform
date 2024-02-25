#FROM gradle:8.5.0-jdk17-alpine AS build
#VOLUME /tmp
#ARG APP_HOME=/educplatform/
#RUN mkdir $APP_HOME
#WORKDIR $APP_HOME
#RUN mkdir "minio-service"
#ARG JAR_FILE=build/libs/educplatform-1.0.0.jar
#COPY build.gradle settings.gradle gradle.properties $APP_HOME
#COPY src src
#RUN gradle clean build
#
#FROM openjdk:17-alpine3.12
#COPY --from=build /educplatform/build/libs/educplatform-1.0.0.jar educplatform.jar
#
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "/educplatform.jar"]

FROM openjdk:17-alpine3.12
COPY build/libs/educplatform-1.0.0.jar educplatform.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/educplatform.jar"]