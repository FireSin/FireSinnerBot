# Используем базовый образ с Java
FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR файл с микросервисом в контейнер
COPY ${JAR_FILE} /app/app.jar

# Команда запуска приложения
CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"app.jar"]