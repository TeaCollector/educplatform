FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} educationplatform.jar
ENTRYPOINT ["java","-jar","/educationplatform.jar"]
