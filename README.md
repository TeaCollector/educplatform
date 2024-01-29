# Education platform

## Для запуска приложения:

1. Установить Docker.
2. Клонировать к себе Git репозиторий командой:
   `git clone https://github.com/TeaCollector/educplatform.git` \
   Либо использовать Intellij Idea:
   `File` -> `New` -> `Project from Version Control` -> В появившемся окне, в строке URL
   ввести: `https://github.com/TeaCollector/educplatform.git` -> Нажать внизу кнопку `Clone`
3. В корне проекта запустить файл `docker-compose.yaml`
4. Подождать минуты 3 пока скачиваются необходимые образы, зависимости и билдится проект внутри контейнера.
5. Так же, в корне проекта находится коллекция запросов в формате json, их необходимо импортировать в Postman.
6. Для тестирования главного модуля: перейти в браузере по адресу `http://localhost:8081/swagger-ui/index.html`
7. Для тестирования сервиса работающего с min.io перейти по адресу: `http://localhost:8082/swagger-ui.html` \
   \
   Видео для тестирования
   min.io: `https://file-examples.com/storage/fed61549c865b2b5c9768b5/2017/04/file_example_MP4_640_3MG.mp4`    