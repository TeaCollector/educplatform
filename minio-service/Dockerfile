FROM openjdk:17-alpine3.12
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} minioservice.jar
ENTRYPOINT ["java","-jar","/minioservice.jar"]