# Education platform

## Для запуска приложения: 

1. Установить Docker.
2. Клонировать к себе Git репозиторий командой:
`git clone https://github.com/TeaCollector/educplatform.git`
<br>
Либо использовать IntellijIdea:
`File` -> `New` -> `Project from Version Control` -> В появившемся окне, в строке URL ввести: `https://github.com/TeaCollector/educplatform.git` -> Нажать внизу кнопку `Clone`
3. В корне проекта запустить файл `docker-compose.yaml`
4. Подождать минуты 3 пока скачиваются необходимые образы, зависимости и билдится проект внутри контейнера.
5. Для тестирования главного модуля: перейти в браузере по адресу `http://localhost:8081/swagger-ui/index.html` 
<br>
6. Для тестирования сервиса работающего с MIN.IO: перейти в браузере по адресу `http://localhost:8082/swagger-ui/index.html`