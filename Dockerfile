FROM openjdk:17-alpine3.12
VOLUME /tmp
ARG JAR_FILE=build/libs/educplatform-1.0.0.jar
COPY ${JAR_FILE} educplatform-1.0.0.jar
ENTRYPOINT ["java","-jar","/educplatform-1.0.0.jar"]

# Используем offisiell OpenJDK 8 Alpine базовый образ
#FROM openjdk:17-alpine
#
## Устанавливаем переменные окружения
#ENV APP_HOME=/usr/app/
#ARG JAR_NAME=educplatform-1.0.0.jar
#
## Создаем директорию для приложения
#RUN mkdir $APP_HOME
#WORKDIR $APP_HOME
#
## Копируем build.gradle и settings.gradle в образ
#COPY build.gradle settings.gradle $APP_HOME
#
## Подтягиваем зависимости, чтобы ускорить процесс сборки
#RUN gradle clean build --no-daemon
#
## Копируем исходный код в образ
#COPY src $APP_HOME/src
#
## Собираем приложение
#RUN gradle clean build --no-daemon
#
## Устанавливаем порт для контейнера
#EXPOSE 8081
#
## Запускаем приложение
#CMD ["java","-jar","build/libs/${JAR_NAME}"]